package com.root34.aurora.mail.service;

import com.root34.aurora.alert.service.AlertService;
import com.root34.aurora.common.FileDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.mail.config.ImapProperties;
import com.root34.aurora.mail.dao.MailMapper;
import com.root34.aurora.mail.dto.MailDTO;
import com.root34.aurora.mail.dto.TagDTO;
import com.root34.aurora.member.dto.MemberDTO;
import com.root34.aurora.report.dto.MailSearchCriteria;
import com.root34.aurora.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.*;
import java.util.*;

/**
 @ClassName : MailService
 @Date : 23.03.20.
 @Writer : 김수용
 @Description : 메일관련 요청을 처리할 Service
 */
@Slf4j
@Service
public class MailService {

    @Value("${file.file-dir}")
    private String FILE_DIR;

    @Autowired
    private final ImapProperties imapProperties;
    private final JavaMailSender javaMailSender;
    private final MailMapper mailMapper;
    private final AlertService alertService;

    @Autowired
    public MailService(JavaMailSender javaMailSender, ImapProperties imapProperties, MailMapper mailMapper, AlertService alertService) {

        this.imapProperties = imapProperties;
        this.javaMailSender = javaMailSender;
        this.mailMapper = mailMapper;
        this.alertService = alertService;
    }

    /**
     * @MethodName : sendMail
     * @Date : 2023-04-10
     * @Writer : 김수용
     * @Description : 메일 전송
     */
    public boolean sendMail(MailDTO mailDTO, List<MultipartFile> fileList) throws IOException, MessagingException {

        log.info("[MailService] sendEmail Start");
        MemberDTO memberDTO = mailMapper.selectMemberDetailByMemberCode(mailDTO.getMemberCode());
        mailDTO.setMemberDTO(memberDTO);
        mailDTO.setSenderName(memberDTO.getMemberName());
        mailDTO.setSenderEmail(memberDTO.getMemberEmail());
        log.info("[MailService] mailDTO : " + mailDTO);

        int result = mailMapper.saveMail(mailDTO);
        log.info("[MailService] saveMail result : " + result);

        MimeMessage message = javaMailSender.createMimeMessage();
        log.info("[MailService] message : " + message);

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress(memberDTO.getMemberEmail(), mailDTO.getSenderName())); // 발신자 이메일, 발신자 명
        helper.setTo(mailDTO.getRecipient());
        helper.setSubject(mailDTO.getMailTitle());
        helper.setText(mailDTO.getMailBody());

        // 참조 목록 추가
//        if (mailDTO.getCc() != null && !mailDTO.getCc().isEmpty()) {
//
//            helper.setCc(mailDTO.getCc().split(","));
//        }
//        log.info("[MailService] helper : " + helper);

        // 숨은 참조 목록 추가
        if (mailDTO.getCc() != null && !mailDTO.getCc().isEmpty()) {

            helper.setBcc(mailDTO.getCc().split(","));
        }
        log.info("[MailService] helper : " + helper);

        // 파일 첨부
        if (fileList != null) {

            int fileCount = 0;

            for (MultipartFile file : fileList) {

                // 메일에 첨부
                String fileName = file.getOriginalFilename();
                log.info("[MailService] fileName : " + fileName);
                helper.addAttachment(fileName, new ByteArrayResource(file.getBytes()));

                // DB에 저장
                fileName = UUID.randomUUID().toString().replace("-", "");
                String replaceFileName = null;
                log.info("[ReportService] FILE_DIR : " + FILE_DIR);
                log.info("[ReportService] fileName : " + fileName);

                replaceFileName = FileUploadUtils.saveFile(FILE_DIR, fileName, file);
                log.info("[ReportService] replaceFileName : " + replaceFileName);

                FileDTO fileDTO = new FileDTO();
                fileDTO.setFileOriginName(file.getOriginalFilename());
                fileDTO.setFileName(replaceFileName);
                fileDTO.setFilePath(FILE_DIR + replaceFileName);
                fileDTO.setMailCode(mailDTO.getMailCode());

                double fileSizeInBytes = (double) file.getSize();

                String fileSizeString = fileSizeInBytes < (1024 * 1024)?
                        String.format("%.2f KB", fileSizeInBytes / 1024) :
                        String.format("%.2f MB", fileSizeInBytes / (1024 * 1024));
                log.info("[ReportService] fileSizeString : " + fileSizeString);
                fileDTO.setFileSize(fileSizeString);
                log.info("[ReportService] fileDTO : " + fileDTO);

                fileCount += mailMapper.registerFileWithMailCode(fileDTO);
                log.info("[ReportService] fileCount : " + fileCount);
            }
        }
        javaMailSender.send(message); // void 문제 발생시 런타임 에러 발생
        log.info("[MailService] sent");

        alertService.registerMailAlert(mailDTO.getSenderEmail(), mailDTO.getRecipient(), "메일", mailDTO.getMailCode());

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

                    List<FileDTO> fileList = saveAttachmentsAndGetFileList(message, FILE_DIR, mailDTO.getMailCode());

                    if(fileList != null) {

                        int fileCount = 0;

                        for (FileDTO fileDTO : fileList) {

                            fileCount += mailMapper.registerFileWithMailCode(fileDTO);
                        }
                        log.info("[ReportService] MailService : " + fileCount);
                    }
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

        for(MailDTO mailDTO : mailList) {

            TagDTO tagDTO = mailMapper.selectTagDetailByTagCode(mailDTO.getTagCode());
            mailDTO.setTagDTO(tagDTO);
        }
        log.info("[MailService] mailList : " + mailList);

        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setData(mailList);
        responseDTOWithPaging.setPageInfo(selectCriteria);
        log.info("[MailService] responseDTOWithPaging : " + responseDTOWithPaging);

        return responseDTOWithPaging;
    }

    /**
    	* @MethodName : selectMailDetailByMailCode
    	* @Date : 2023-04-12
    	* @Writer : 김수용
    	* @Description : 메일 상세 조회
    */
    public MailDTO selectMailDetailByMailCode(int memberCode, long mailCode) {

        log.info("[MailService] selectMailDetailByMailCode Start");
        log.info("[MailService] memberCode : " + memberCode);
        log.info("[MailService] mailCode : " + mailCode);

        MemberDTO member = mailMapper.selectMemberDetailByMemberCode(memberCode);
        log.info("[MailService] member : " + member);

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("mailCode", mailCode);
        parameters.put("memberEmail", member.getMemberEmail());
        log.info("[MailService] parameters : " + parameters);

        MailDTO mailDTO = mailMapper.selectMailDetailByMailCode(parameters);

        TagDTO tagDTO = mailMapper.selectTagDetailByTagCode(mailDTO.getTagCode());
        mailDTO.setTagDTO(tagDTO);

        String memberEmail = mailDTO.getSenderEmail();
        MemberDTO senderMember = mailMapper.selectMemberDetailByEmail(memberEmail);

        if (senderMember != null) {
            mailDTO.setMemberDTO(senderMember);
        }
        mailDTO.setFileList(mailMapper.selectAttachmentListByMailCode(mailCode));
        log.info("[MailService] mailDTO : " + mailDTO);

        return mailDTO;
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
    	* @MethodName : updateDeleteStatus
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 메일 삭제 상태 수정
    */
    public boolean updateDeleteStatus(List<Long> mailCodeList, char deleteStatus) {

        log.info("[MailService] updateDeleteStatus Start");
        log.info("[MailService] mailCodeList : " + mailCodeList);
        log.info("[MailService] deleteStatus : " + deleteStatus);

        int deleteCount = 0;

        for(long mailCode : mailCodeList) {

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("mailCode", mailCode);
            parameters.put("deleteStatus", deleteStatus);

            deleteCount += mailMapper.updateDeleteStatus(parameters);
        }
        log.info("[MailService] updateDeleteStatus deleteCount : " + deleteCount);

        return deleteCount > 0;
    }

    /**
    	* @MethodName : updateMailTag
    	* @Date : 2023-04-12
    	* @Writer : 김수용
    	* @Description : 메일 태그 변경
    */
    public boolean updateMailTag(long mailCode, long tagCode) {

        log.info("[MailService] updateMailTag Start");
        log.info("[MailService] mailCode : " + mailCode);
        log.info("[MailService] tagCode : " + tagCode);

        MailDTO mailDTO = new MailDTO();
        mailDTO.setMailCode(mailCode);
        mailDTO.setTagCode(tagCode);
        log.info("[MailService] mailDTO : " + mailDTO);

        int result = mailMapper.updateMailTag(mailDTO);
        log.info("[MailService] result : " + result);

        return result > 0;
    }

    /**
     * @MethodName : deleteMail
     * @Date : 2023-04-12
     * @Writer : 김수용
     * @Description : 메일 완전 삭제
     */
    public boolean deleteMail(List<Long> mailCodeList) {

        log.info("[MailService] deleteMail Start");
        log.info("[MailService] mailCodeList : " + mailCodeList);

        int deleteResult = 0;

        for(long mailCode : mailCodeList) {

            deleteResult += mailMapper.deleteMail(mailCode);
        }
        log.info("[MailService] deleteResult : " + deleteResult);

        return deleteResult > 0;
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
        log.info("[MailService] registerTag result : " + result);

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
        log.info("[MailService] tagList : " + tagList);

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
        log.info("[MailService] updateTag result : " + result);

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
        log.info("[MailService] deleteTag result : " + result);

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
        log.info("[MailService] message : " + message);

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
        log.info("[MailService] mimeMultipart : " + mimeMultipart);
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
    	* @MethodName : saveAttachmentsAndGetFileList
    	* @Date : 2023-04-12
    	* @Writer : 김수용
    	* @Description : 첨부파일 저장
    */
    private List<FileDTO> saveAttachmentsAndGetFileList(Message message, String outputDirectory, Long mailCode) throws Exception {

        log.info("[MailService] getTextFromMimeMultipart Start");
        log.info("[MailService] message : " + message);
        log.info("[MailService] outputDirectory : " + outputDirectory);

        List<FileDTO> fileList = new ArrayList<>();

        if (message.getContent() instanceof Multipart) {

            Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {

                BodyPart bodyPart = multipart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) || bodyPart.getContentType().toLowerCase().startsWith("image/")) {

                    String fileName = bodyPart.getFileName();
                    String fileExtension = getFileExtension(fileName);
                    String uuidFileName = UUID.randomUUID().toString().replace("-", "") + fileExtension;

                    InputStream inputStream = bodyPart.getInputStream();
                    File outputFile = new File(outputDirectory + File.separator + uuidFileName);
                    FileOutputStream outputStream = new FileOutputStream(outputFile);

                    byte[] buffer = new byte[4096];

                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {

                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.close();
                    inputStream.close();

                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setFileName(uuidFileName);
                    fileDTO.setFileOriginName(fileName);
                    String relativeFilePath = "/" + outputFile.getAbsolutePath().substring(outputFile.getAbsolutePath().indexOf("auroraFiles"));
                    relativeFilePath = relativeFilePath.replace("\\", "/");
                    fileDTO.setFilePath(relativeFilePath);
                    fileDTO.setMailCode(mailCode);


                    double fileSizeInBytes = (double) outputFile.length();
                    String fileSizeString = fileSizeInBytes < (1024 * 1024) ?
                            String.format("%.2f KB", fileSizeInBytes / 1024) :
                            String.format("%.2f MB", fileSizeInBytes / (1024 * 1024));
                    fileDTO.setFileSize(fileSizeString);

                    fileList.add(fileDTO);
                    log.info("[MailService] fileList : " + fileList);
                }
            }
        }
        return fileList;
    }

    /**
     * @MethodName : getFileExtension
     * @Date : 2023-04-13
     * @Writer : 김수용
     * @Description : 파일 확장자 가져오기
     */
    private String getFileExtension(String fileName) {

        int index = fileName.lastIndexOf(".");

        if (index == -1) {

            return "";
        }
        return fileName.substring(index);
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
//        searchConditions.put("blacklist", searchCriteria.getBlacklist());
        log.info("[MailController] searchConditions : " + searchConditions);

        return searchConditions;
    }
}
