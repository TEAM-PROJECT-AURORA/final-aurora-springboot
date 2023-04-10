package com.root34.aurora.mail.service;

import com.root34.aurora.configuration.MailConfig;
import com.root34.aurora.mail.dto.MailDTO;
import com.root34.aurora.report.controller.ReportControllerTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@Slf4j
@SpringBootTest
public class MailServiceTest {

    @Value("${file.file-dir}")
    private String FILE_DIR;
    @Value("${file.file-url}")
    private String FILE_URL;

    private static final Logger log = LoggerFactory.getLogger(ReportControllerTest.class);

//    private final MailConfig mailConfig;
    private final JavaMailSender javaMailSender;

    @Autowired
    private MailService mailService;

    @Autowired
    public MailServiceTest(/*JavaMailSender javaMailSender, */MailConfig mailConfig) {

//        this.mailConfig = mailConfig;
        this.javaMailSender = mailConfig.javaMailSender();
    }

    @Test
    public void 메일_서비스_의존성_주입_테스트() {

        assertNotNull(javaMailSender);
    }

    @Test
    void 메일_전송_서비스_테스트() throws IOException, MessagingException {

        // given
        MailDTO mailDTO = new MailDTO();
        mailDTO.setSenderName("테스트 이름");
//        mailDTO.setSender("admin@project-aurora.co.kr");
        mailDTO.setSenderEmail("admin@project-aurora.co.kr");
//        mailDTO.setSender("ssssong125@project-aurora.co.kr");

//        mailDTO.setRecipient("admin@gmail.com");
//        mailDTO.setRecipient("jgh337337@gmail.com");
//        mailDTO.setRecipient("os133517@gmail.com");
//        mailDTO.setRecipient("admin@project-aurora.co.kr");
        mailDTO.setRecipient("test02@project-aurora.co.kr");
//        mailDTO.setRecipient("ssssong125@gmail.com");
        mailDTO.setMailTitle("Test");
        mailDTO.setMailBody("Test Body aaa");
//        MockMultipartFile file = new MockMultipartFile("file", "testAuroraImage.jpg", "image/jpg",
//                                                        new FileInputStream("C:\\auroraFiles\\testAuroraImage.jpg"));

        // when
        boolean result = mailService.sendMail(mailDTO);

        // then
        assertEquals(true, result);
    }

    @Test
    void 메일_조회_서비스_테스트() {

        // given

        // when
        mailService.readUnseenMails();

        // then
    }
}

//            Store store = emailSession.getStore("imap");
//            log.info("[MailService] emailSession properties : " + emailSession.getProperties());
//
//            store.connect(imapProperties.getHost(), imapProperties.getUsername(), imapProperties.getPassword());
////            store.connect(host, username, password); // imap host
//
//            Folder inbox = store.getFolder("INBOX");
//            inbox.open(Folder.READ_ONLY);
//
//            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
//
//            for (Message message : messages) {
//
//                Address[] senders = message.getFrom();
//                Address[] recipients = message.getAllRecipients();
//
//                boolean containsDomain = false;
//
//                for (Address sender : senders) {
//
//                    if (sender.toString().contains(domain)) {
//
//                        containsDomain = true;
//                        break;
//                    }
//                }
//                if (!containsDomain) {
//
//                    for (Address recipient : recipients) {
//
//                        if (recipient.toString().contains(domain)) {
//
//                            containsDomain = true;
//                            break;
//                        }
//                    }
//                }
//                if (containsDomain) {
//
//                    MailDTO mail = new MailDTO();
//                    mail.setMailTitle(message.getSubject());
//                    mail.setMailBody(getTextFromMessage(message));
//                    mail.setSender(senders[0].toString());
//                    mail.setRecipient(Arrays.toString(recipients));
//                    projectMails.add(mail);
//                }
//            }
//            inbox.close(false);
//
//            store.close();
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return projectMails;
//    }