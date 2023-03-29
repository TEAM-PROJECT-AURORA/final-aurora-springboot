package com.root34.aurora.report.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.root34.aurora.jwt.JwtFilter;
import com.root34.aurora.jwt.TokenProvider;
import com.root34.aurora.report.dto.ReportDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//@WebMvcTest(ReportController.class)
//@ExtendWith(SpringExtension.class) // @WebMvcTest 사용할 시 추가할 필요 x

@SpringBootTest
@AutoConfigureMockMvc
@Import({JwtFilter.class, TokenProvider.class})
public class ReportControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ReportControllerTest.class);
    private final int MEMBER_CODE = 1;
    private final String REPORT_TYPE = "Routine";
    private final char COMPLETION_STATUS = 'N';

    private final String TOKEN =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MDEiLCJhdXRoIjpbIlJPTEVfVVNFUiJdLCJtZW1iZXJDb2RlIjoxLCJleHAiOjE2ODAxNTI2Nzd9.yhWufM9QxjNbgcn-L6NN6cr80G_Q-G7vq0WIMWb8UYXVj1ywafSJFZTU8LomBP5vYLwqp_r1CmCkf8mZuP76BA"; // JWT

    @Autowired
    private ReportController reportController;

//    @Autowired
//    private ReportService reportService;

    // MockMvc 애플리케이션을 서버에 배포하지 않고도 스프링의 MVC 테스트를 할 수 있게 해준다.
//    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testInit(){

        assertNotNull(reportController);
        assertNotNull(mockMvc);
    }

    //    @BeforeAll // 테스트 클래스의 모든 테스트 메소드 실행 전에 딱 한 번만 실행
    @BeforeEach // 각 테스트 메소드 실행 전에 매번 실행
    public void setUp(){

        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    void 보고_작성_컨트롤러_테스트() throws Exception {

        // given
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setMemberCode(MEMBER_CODE);
        reportDTO.setReportType(REPORT_TYPE);
        reportDTO.setReportTitle("TestReportTitle");
        reportDTO.setReportInfo("TestReportInfo");
        reportDTO.setReportCycle("Mon");

        List<Integer> memberList = new ArrayList<>();
        memberList.add(2);
        memberList.add(3);

        // 가상의 파일 생성
        MockMultipartFile file1 = new MockMultipartFile("file1.txt", "file1.txt", "text/plain", "This is file1 content".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file2.txt", "file2.txt", "text/plain", "This is file2 content".getBytes());

        List<MockMultipartFile> fileList = new ArrayList<>();
        fileList.add(file1);
        fileList.add(file2);

        // JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String reportDTOJson = objectMapper.writeValueAsString(reportDTO);
        String memberListJson = objectMapper.writeValueAsString(memberList);
        log.info("reportDTOJson" + reportDTOJson);
        log.info("memberListJson" + memberListJson);

        // MockMultipartFile 형태로 변환
        MockMultipartFile reportDtoMultipartFile = new MockMultipartFile("reportDTO", "", "application/json", reportDTOJson.getBytes());
        MockMultipartFile memberListMultipartFile = new MockMultipartFile("memberList", "", "application/json", memberListJson.getBytes());

        // when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/reports")
//                .post("/api/v1/reports")
//                .multipart("/api/v1/reports")
                .multipart("http://localhost:8090/api/v1/reports")
                .file("fileList", file1.getBytes()) // Add files with the proper key
                .file("fileList", file2.getBytes()) // Add files with the proper key
                .file(reportDtoMultipartFile)
                .file(memberListMultipartFile)
//                .param("reportDTO", reportDTOJson)
//                .param("memberList", memberListJson)
                .header("Authorization", "Bearer " + TOKEN)
//                .contentType(MediaType.APPLICATION_JSON);
                .contentType(MediaType.MULTIPART_FORM_DATA);

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

//    @Test
//    @Transactional
//    @Rollback(false)
//    void 보고_작성_컨트롤러_테스트() {
//
//        // given
//        Map<String, Object> requestData = new HashMap<String, Object>();
//
//        ReportDTO reportDTO = new ReportDTO();
//        reportDTO.setMemberCode(1);
//        reportDTO.setReportType("Routine");
//        reportDTO.setReportTitle("TestReportTitle");
//        reportDTO.setReportInfo("TestReportInfo");
//        reportDTO.setReportCycle("Mon");
//
//        List<Integer> memberList = new ArrayList<>();
//        memberList.add(2);
//        memberList.add(3);
//
//        requestData.put("reportDTO", reportDTO);
//        requestData.put("memberList", memberList);
//
//        try {
//            String requestDataJson = objectMapper.writeValueAsString(requestData);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        // when
//        mockMvc.perform(
//                post("/saveRequestData")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestDataJson)
//        )
//                .andExpect(status().isOk());
//    }
}
