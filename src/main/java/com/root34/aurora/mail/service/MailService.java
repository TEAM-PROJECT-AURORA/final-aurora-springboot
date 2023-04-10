package com.root34.aurora.mail.service;

import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.mail.config.ImapProperties;
import com.root34.aurora.mail.dao.MailMapper;
import com.root34.aurora.mail.dto.MailDTO;
import com.root34.aurora.mail.dto.TagDTO;
import com.root34.aurora.member.dto.MemberDTO;
import com.root34.aurora.report.dto.MailSearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 @ClassName : MailService
 @Date : 23.03.20.
 @Writer : 김수용
 @Description : 메일관련 요청을 처리할 Service
 */
@Slf4j
@Service
public class MailService {

    @Autowired
    private final ImapProperties imapProperties;
    private final JavaMailSender javaMailSender;
    private final MailMapper mailMapper;
    private Session emailSession;

    private final String domain = "@project-aurora.co.kr";

    @Autowired
    public MailService(JavaMailSender javaMailSender, ImapProperties imapProperties, MailMapper mailMapper) {

        this.imapProperties = imapProperties;
        this.javaMailSender = javaMailSender;
        this.mailMapper = mailMapper;
    }

    /**
     * @MethodName : sendMail
     * @Date : 2023-04-10
     * @Writer : 김수용
     * @Description : 메일 전송
     */
    public boolean sendMail(MailDTO mailDTO) throws UnsupportedEncodingException, MessagingException {

        log.info("[MailService] sendEmail Start");
        MemberDTO memberDTO = mailMapper.selectMemberDetail(mailDTO.getMemberCode());
        mailDTO.setMemberDTO(memberDTO);
        mailDTO.setSenderName(memberDTO.getMemberName());
        mailDTO.setSenderEmail(memberDTO.getMemberEmail());
        log.info("[MailService] mailDTO : " + mailDTO);

        MimeMessage message = javaMailSender.createMimeMessage();
        log.info("[MailService] message : " + message);

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress(memberDTO.getMemberEmail(), mailDTO.getSenderName())); // 발신자 이메일, 발신자 명
        helper.setTo(mailDTO.getRecipient());
        helper.setSubject(mailDTO.getMailTitle());
        helper.setText(mailDTO.getMailBody());
        log.info("[MailService] helper : " + helper);

        javaMailSender.send(message); // void 문제 발생시 런타임 에러 발생
        log.info("[MailService] sent");

        int result = mailMapper.saveMail(mailDTO);
        log.info("[MailService] saveMail result : " + result);

        return result > 0;
    }

    /**
     * @MethodName : readUnseenMails
     * @Date : 2023-04-10
     * @Writer : 김수용
     * @Description : 읽지 않은 메일 조회
     */
    public void readUnseenMails() {

        log.info("[MailService] sendEmail Start");

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", imapProperties.getHost());
        properties.put("mail.imaps.port", imapProperties.getPort());
        properties.put("mail.imaps.ssl.enable", imapProperties.isSslEnable());
        log.info("[MailService] properties : " + properties);

        try {
            Session session = Session.getDefaultInstance(properties, null);
            Store store = session.getStore("imaps");
            store.connect(imapProperties.getUsername(), imapProperties.getPassword());

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            System.out.println("Number of unseen messages: " + messages.length);
            log.info("[MailService] Number of unseen messages : " + messages.length);

            int matchedMailCount = 0;

            for (Message message : messages) {

                Address[] fromAddresses = message.getFrom();
                Address[] toAddresses = message.getRecipients(Message.RecipientType.TO);

                boolean match = false;
                for (Address address : fromAddresses) {

                    if (address.toString().contains("@project-aurora.co.kr")) {

                        match = true;
                        break;
                    }
                }
                if (!match) {
                    for (Address address : toAddresses) {

                        if (address.toString().contains("@project-aurora.co.kr")) {

                            match = true;
                            break;
                        }
                    }
                }
                if (match) {

                    System.out.println("---------------------------------");
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("From: " + message.getFrom()[0]);
                    System.out.println("Content: " + message.getContent().toString());

                    // 발신자 이름, 이메일 분리
                    String senderName = null;
                    String senderEmail = null;

                    if (message.getFrom()[0] instanceof InternetAddress) {
                        InternetAddress fromAddress = (InternetAddress) message.getFrom()[0];
                        senderName = fromAddress.getPersonal();
                        senderEmail = fromAddress.getAddress();
                    } else {
                        String[] parts = message.getFrom()[0].toString().split(" <");
                        senderName = parts[0];
                        senderEmail = parts[1].substring(0, parts[1].length() - 1);
                    }
                    // 메일 내용 변환
                    String content = getTextFromMessage(message);

                    MailDTO mailDTO = new MailDTO();
                    mailDTO.setSenderName(senderName);
                    mailDTO.setSenderEmail(senderEmail);
                    mailDTO.setRecipient(toAddresses[0].toString());
                    mailDTO.setMailTitle(message.getSubject());
                    mailDTO.setMailBody(content);
                    log.info("[MailService] mailDTO : " + mailDTO);

                    matchedMailCount += mailMapper.saveMail(mailDTO);

                    message.setFlag(Flags.Flag.SEEN, true); // 메일 읽음 상태로 전환
                }
            }
            log.info("[MailService] matchedMailCount : " + matchedMailCount);

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    	* @MethodName : selectMailListByConditions
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 조건별 메일 목록 조회
    */
    public ResponseDTOWithPaging selectMailListByConditions(int memberCode, int offset, HashMap<String, Object> searchConditions) {

        log.info("[MailService] selectMailListByConditions Start");
        log.info("[MailService] offset : " + offset);
        log.info("[MailService] searchConditions : " + searchConditions);

        List<String> blackList = mailMapper.selectBlackListByMemberCode(memberCode);
        log.info("[MailService] blackList : " + blackList);
        searchConditions.put("blackList", blackList);

        int totalCount = mailMapper.getMailCount(searchConditions);
        log.info("[MailService] totalCount : " + totalCount);
        int limit = 10;
        log.info("[MailService] limit : " + limit);
        int buttonAmount = 5;
        log.info("[MailService] buttonAmount : " + buttonAmount);

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(offset, totalCount, limit, buttonAmount);
        selectCriteria.setSearchConditions(searchConditions);
        log.info("[MailService] selectCriteria : " + selectCriteria);

        List<MailDTO> mailList = mailMapper.selectMailListByConditions(selectCriteria);
        // 여유되면 이메일로 멤버 상세정보 구해서 넣는 로직 추가
        log.info("[MailService] mailList : " + mailList);

        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setData(mailList);
        responseDTOWithPaging.setPageInfo(selectCriteria);
        log.info("[MailService] responseDTOWithPaging : " + responseDTOWithPaging);

        return responseDTOWithPaging;
    }

    /**
    	* @MethodName : updateImportantStatus
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 메일 중요 상태 수정
    */
    public boolean updateImportantStatus(MailDTO mailDTO) {

        log.info("[MailService] updateImportantStatus Start");
        log.info("[MailService] mailDTO : " + mailDTO);

        int result = mailMapper.updateImportantStatus(mailDTO);
        log.info("[MailService] updateImportantStatus result : " + result);

        return result > 0;
    }

    /**
    	* @MethodName : registerTag
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 태그 생성
    */
    public boolean registerTag(TagDTO tagDTO) {

        log.info("[MailService] registerTag Start");
        log.info("[MailService] tagDTO : " + tagDTO);

        int result = mailMapper.registerTag(tagDTO);

        return result > 0;
    }

    /**
    	* @MethodName : updateDeleteStatus
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 메일 삭제 상태 수정
    */
    public boolean updateDeleteStatus(MailDTO mailDTO) {

        log.info("[MailService] updateDeleteStatus Start");
        log.info("[MailService] mailDTO : " + mailDTO);

        int result = mailMapper.updateDeleteStatus(mailDTO);
        log.info("[MailService] updateDeleteStatus result : " + result);

        return result > 0;
    }

    /**
    	* @MethodName : selectTagListByMemberCode
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 태그 목록 조회
    */
    public List<TagDTO> selectTagListByMemberCode(int memberCode) {

        log.info("[MailService] selectTagListByMemberCode Start");
        log.info("[MailService] memberCode : " + memberCode);

        List<TagDTO> tagList = mailMapper.selectTagListByMemberCode(memberCode);

        return tagList;
    }

    /**
    	* @MethodName : updateTag
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 태그 수정
    */
    public boolean updateTag(TagDTO tagDTO) {

        log.info("[MailService] updateTag Start");
        log.info("[MailService] tagDTO : " + tagDTO);

        int result = mailMapper.updateTag(tagDTO);

        return result > 0;
    }

    /**
    	* @MethodName : deleteTag
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 태그 삭제
    */
    public boolean deleteTag(long tagCode) {

        log.info("[MailService] deleteTag Start");
        log.info("[MailService] tagCode : " + tagCode);

        int result = mailMapper.deleteTag(tagCode);

        return result > 0;
    }

    /**
    	* @MethodName : registerBlockList
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 블랙리스트 등록
    */
    public boolean registerBlackList(int memberCode, List<String> emails) {

        log.info("[MailService] registerBlockList Start");
        log.info("[MailService] memberCode : " + memberCode);
        log.info("[MailService] emails : " + emails);

        int blackListCount = 0;

        for(String email : emails) {

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("memberCode", memberCode);
            parameters.put("email", email);
            log.info("[MailService] parameters : " + parameters);

            blackListCount += mailMapper.registerBlackList(parameters);
        }
        log.info("[MailService] blackListCount : " + blackListCount);
        return blackListCount > 0;
    }

    /**
     * @MethodName : selectBlackListByMemberCode
     * @Date : 2023-04-10
     * @Writer : 김수용
     * @Description : 블랙리스트 조회
     */
    public List<String> selectBlackListByMemberCode(int memberCode) {

        log.info("[MailService] selectBlockListByMemberCode Start");
        log.info("[MailService] memberCode : " + memberCode);

        List<String> blackList = mailMapper.selectBlackListByMemberCode(memberCode);
        log.info("[MailService] blackList : " + blackList);

        return blackList;
    }

    /**
     * @MethodName : deleteBlockedSenderEmail
     * @Date : 2023-04-10
     * @Writer : 김수용
     * @Description : 블랙리스트에서 삭제
     */
    public boolean deleteBlockedSenderEmail(int memberCode, List<String> emails) {

        log.info("[MailService] deleteBlockedSenderEmail Start");
        log.info("[MailService] memberCode : " + memberCode);
        log.info("[MailService] emails : " + emails);

        int deletedBlackListCount = 0;

        for(String blockedSender : emails) {

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("memberCode", memberCode);
            parameters.put("blockedSender", blockedSender);
            log.info("[MailService] parameters : " + parameters);

            deletedBlackListCount += mailMapper.deleteBlockedSenderEmail(parameters);
        }
        log.info("[MailService] deletedBlackListCount : " + deletedBlackListCount);

        return deletedBlackListCount > 0;
    }

    /**
    	* @MethodName : getTextFromMessage
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : html을 텍스트로 변환
    */
    private String getTextFromMessage(Message message) throws MessagingException, IOException, IOException {

        log.info("[MailService] getTextFromMessage Start");
        String result = "";

        if (message.isMimeType("text/plain")) {

            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {

            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        log.info("[MailService] result : " + result);
        return result;
    }

    /**
    	* @MethodName : getTextFromMimeMultipart
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : MIME 타입 복합 메시지에서 텍스트 부분 추출
    */
    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {

        log.info("[MailService] getTextFromMimeMultipart Start");
        StringBuilder result = new StringBuilder();

        int count = mimeMultipart.getCount();

        for (int i = 0; i < count; i++) {

            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {

                result.append("\n").append(bodyPart.getContent());
                break;
            } else if (bodyPart.isMimeType("text/html")) {

                String html = (String) bodyPart.getContent();
                result.append("\n").append(Jsoup.parse(html).text());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {

                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        log.info("[MailService] result : " + result.toString());
        return result.toString();
    }

    /**
     * @MethodName : convertSearchCriteriaToMap
     * @Date : 2023-04-10
     * @Writer : 김수용
     * @Description : 검색조건 변환
     */
    public HashMap<String, Object> convertSearchCriteriaToMap(MailSearchCriteria searchCriteria) {

        log.info("[MailController] convertSearchCriteriaToMap Start");
        log.info("[MailController] searchCriteria : " + searchCriteria);

        HashMap<String, Object> searchConditions = new HashMap<>();
        searchConditions.put("tagCode", searchCriteria.getTagCode());
        searchConditions.put("mailTitle", searchCriteria.getMailTitle());
        searchConditions.put("mailBody", searchCriteria.getMailBody());
        searchConditions.put("senderName", searchCriteria.getSenderName());
        searchConditions.put("senderEmail", searchCriteria.getSenderEmail());
        searchConditions.put("recipients", searchCriteria.getRecipients());
        searchConditions.put("startDate", searchCriteria.getStartDate());
        searchConditions.put("endDate", searchCriteria.getEndDate());
        searchConditions.put("importantStatus", searchCriteria.getImportantStatus());
        searchConditions.put("deleteStatus", searchCriteria.getDeleteStatus());
        searchConditions.put("blacklist", searchCriteria.getBlacklist());
        log.info("[MailController] searchConditions : " + searchConditions);

        return searchConditions;
    }
}
//    private JavaMailSender mailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("your_gmail_username");
//        mailSender.setPassword("your_gmail_password");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }

//    private JavaMailSenderImpl createJavaMailSender(MailAccountDTO senderAccount) {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setUsername(senderAccount.getUsername());
//        mailSender.setPassword(senderAccount.getPassword());
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }

//    private MailAccountDTO getSenderAccountByEmail(String email) {
//
//        return mailSenderAccounts.stream()
//                .filter(account -> account.getUsername().equalsIgnoreCase(email))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("No matching mail account found for email: " + email));
//    }

//    private final MailConfig mailConfig;
//    private final JavaMailSender javaMailSender;

//    @Autowired
//    public MailService(MailConfig mailConfig, JavaMailSender javaMailSender) {
//
//        this.mailConfig = mailConfig;
//        this.javaMailSender = javaMailSender;
//    }

// 이메일 보내는 메서드
//    public void sendEmail(String to, String subject, String text) {
//    public boolean sendEmail(MailDTO mailDTO) {
//
//        MimeMessage message = javaMailSender.createMimeMessage();
//
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); // 안 하면 수신이메일 인코딩 문제 발생
//
//            helper.setTo(mailDTO.getRecipient());
//            helper.setSubject(mailDTO.getMailTitle());
//            helper.setText(mailDTO.getMailBody(), true);
////            helper.addAttachment("my_photo.png", new ClassPathResource("my_photo.png"));
//            javaMailSender.send(message);
//
//        } catch (MessagingException e) {
//            // 예외 처리
//            return false;
//        }
//
//        return true;
//    }
///**
// * @MethodName : initEmailSession
// * @Date : 2023-04-10
// * @Writer : 김수용
// * @Description : email session 초기화
// */
//    private void initEmailSession() {
//
//        log.info("[MailService] initEmailSession Start");
//
//        Properties properties = new Properties();
//        properties.put("mail.store.protocol", "imap");
//        properties.put("mail.imap.starttls.enable", "true");
//        properties.put("mail.imap.host", imapProperties.getHost());
//        properties.put("mail.imap.port", imapProperties.getPort());
//        properties.put("mail.imap.ssl.enable", imapProperties.getSsl().isEnable());
//        properties.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.put("mail.imap.socketFactory.fallback", "false");
////        properties.put("mail.imap.connectiontimeout", "10000"); // 10초로 연결 시간 초과 설정 추가
////        properties.put("mail.imap.timeout", "10000"); // 10초로 읽기 시간 초과 설정 추가
//        log.info("[ReportService] properties : " + properties);
//
//        emailSession = Session.getInstance(properties);
//        log.info("[ReportService] emailSession : " + emailSession);
//    }