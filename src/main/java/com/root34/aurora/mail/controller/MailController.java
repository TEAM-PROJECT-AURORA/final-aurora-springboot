package com.root34.aurora.mail.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.mail.dto.MailDTO;
import com.root34.aurora.mail.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Api(tags = {"mails"}) // Swagger
@RestController
@RequestMapping("api/v1")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailsService) {
        this.mailService = mailsService;
    }

    /**
     * @MethodName :
     * @Date :
     * @Writer :
     * @Method Description :
     */
//    @ApiOperation(value = "메일 보내기") // Swagger
    @Transactional
    @PostMapping(value ="/mail")
    public ResponseEntity<ResponseDTO> sendMail(@RequestBody MailDTO mailDTO) {

        if(mailService.sendEmail(mailDTO)) {
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "메일 전송 성공!", true));

        } else {
//            return ResponseEntity.internalServerError().body(ResponseDTO.status(HttpStatus.InternalServerError));
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "메일 전송 실패!", false));
        }
    }
}
