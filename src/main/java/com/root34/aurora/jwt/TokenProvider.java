package com.root34.aurora.jwt;

import com.root34.aurora.exception.TokenException;
import com.root34.aurora.member.dto.MemberDTO;
import com.root34.aurora.member.dto.TokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;     //30분       // 30분
    private final UserDetailsService userDetailsService;
    private final Key key;

    // application.yml 에 정의해놓은 jwt.secret 값을 가져와서 JWT 를 만들 때 사용하는 암호화 키값을 생성
    public TokenProvider(@Value("${jwt.secret}") String secretKey, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes); //secret값을 Base64 Decode해서 key변수에 할당
    }

    // Authentication 객체(유저)의 권한 정보를 이용해서 토큰을 생성
    public TokenDTO generateTokenDto(MemberDTO member) {
        log.info("[TokenProvider] generateTokenDto Start ===================================");
        log.info("[TokenProvider] {}", member.getMemberRole());

        // 권한들 가져오기
        List<String> roles = Collections.singletonList(member.getMemberRole());

        // 유저 권한정보 담기
        Claims claims = Jwts
                .claims()
                .setSubject(member.getMemberId());// sub : subject. 토큰 제목을 나타낸다.
        claims.put(AUTHORITIES_KEY, roles);// 권한 담기
        claims.put("memberCode", member.getMemberCode());

        long now = (new Date()).getTime();// 만료시간 때문에 현재 시간을 구하는 듯

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setClaims(claims)// payload "auth" : "ROLE_USER" // aud : Audience. 토큰 대상자를 나타낸다.
                .setExpiration(accessTokenExpiresIn)// payload "exp" : 1517239022(예시) // exp : Expiration Time. 토큰 만료 시각을 나타낸다.
                .signWith(key, SignatureAlgorithm.HS512)// header "alg" : "HS512" // "alg" : "서명 시 사용하는 알고리즘"
                .compact();

        return new TokenDTO(BEARER_TYPE, member.getMemberName(), accessToken, accessTokenExpiresIn.getTime());// Date 객체에 있는 메소든가보네
    }

    public String getUserId(String accessToken) {

        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }

    // Token 에 담겨있는 정보를 이용해 Authentication 객체 리턴
    public Authentication getAuthentication(String accessToken) {

        // 토큰 복호화
        Claims claims = parseClaims(accessToken);// JWT 토큰을 복호화하여 토큰에 들어 있는 정보를 꺼낸다.

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        log.info("[TokenProvider] authorities : {}", authorities);

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(accessToken));

        // UserDetails 객체를 생성해서 UsernamePasswordAuthenticationToken 형태로 리턴, SecuriyContext 를 사용하기 위한 절차
        // SecurityContext 가 Authentication 객체를 저장하고 있기 때문이다.
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰의 유효성 검증 ,Jwts 이 exception 을 던짐
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("[TokenProvider] 잘못된 JWT 서명입니다.");
            throw new TokenException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("[TokenProvider] 만료된 JWT 토큰입니다.");
            throw new TokenException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("[TokenProvider] 지원되지 않는 JWT 토큰입니다.");
            throw new TokenException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("[TokenProvider] JWT 토큰이 잘못되었습니다.");
            throw new TokenException("JWT 토큰이 잘못되었습니다.");
        }
    }

    // 토큰 정보를 꺼내기 위해서
    private Claims parseClaims(String accessToken) {

        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {// 만료되어도 정보를 꺼내서 던짐
            return e.getClaims();
        }
    }
}
