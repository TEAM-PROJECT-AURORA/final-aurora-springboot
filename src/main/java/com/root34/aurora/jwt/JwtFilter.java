package com.root34.aurora.jwt;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.root34.aurora.exception.dto.ApiExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Client 의 요청을 가로채 header 의 token 이 유효 한지 검증
public class JwtFilter extends OncePerRequestFilter {//OncePerRequestFilter 인터페이스를 구현하기 때문에 요청 받을 때 단 한번만 실행된다

    private static final Logger log = LoggerFactory.getLogger(OncePerRequestFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    // 실제 필터링 로직은 doFilterInternal 에 작성
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("[JwtFilter] doFilterInternal START ===================================");

        try {
            // 1. Request Header 에서 토큰을 꺼냄
            String jwt = resolveToken(request);
            log.info("[JwtFilter] jwt : {}", jwt);

            // 2. 꺼내온 토큰을 validateToken 으로 토큰 유효성 검사
            // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장하기
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 23.03.23 김수용 memberCode 추출하기위해 추가함
                String memberCode = tokenProvider.getMemberCodeFromToken(jwt);
                request.setAttribute("memberCode", memberCode);
            }
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ApiExceptionDto errorResponse = new ApiExceptionDto(HttpStatus.UNAUTHORIZED, e.getMessage());


            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(convertObjectToJson(errorResponse));
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {

        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    // Request Header 에서 토큰 정보를 꺼내오기
    private String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        ///Header에서 Bearer 부분 이하로 붙은 token을 파싱한다.
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}