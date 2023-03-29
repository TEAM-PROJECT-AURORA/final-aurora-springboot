package com.root34.aurora.report.controller;

import com.root34.aurora.report.dto.ReportDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//@WebMvcTest(ReportController.class)
//@ExtendWith(SpringExtension.class) // @WebMvcTest 사용할 시 추가할 필요 x

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ReportControllerTest.class);
    private final int MEMBER_CODE = 1;
    private final String REPORT_TYPE = "Routine";
    private final char COMPLETION_STATUS = 'N';

    private final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MDEiLCJhdXRoIjpbIlJPTEVfVVNFUiJdLCJtZW1iZXJDb2RlIjoxLCJleHAiOjE2ODAxMzkyMTl9.HwLc27HYojV9BR3qX6OKf7qNIT1YnXhXZ1njL2bvCzv_h2kHWjqaL9Ov-qR5GnN0PU7wk5yHHw2YJ3Qt5G-m3g"; // JWT

    // MockMvc 애플리케이션을 서버에 배포하지 않고도 스프링의 MVC 테스트를 할 수 있게 해준다.
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportController reportController;

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
//    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
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
//        byte[] content1 = "Hello, world!".getBytes();
//        String fileName1 = "test.txt";
//        MultipartFile file1 = new MockMultipartFile("file", fileName1, "text/plain", content1);
//
//        byte[] content2 = "Hello, world!".getBytes();
//        String fileName2 = "test.jpg";
//        MultipartFile file2 = new MockMultipartFile("file", fileName2, "jpg/plain", content2);

        MockMultipartFile file1 = new MockMultipartFile("file1", "test1.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World 1!".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file2", "test2.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World 2!".getBytes());

        List<MultipartFile> fileList = new ArrayList<MultipartFile>();
        fileList.add(file1);
        fileList.add(file2);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("reportDTO", reportDTO);
        params.add("memberList", memberList);
        params.add("fileList", fileList);
//        params.add("fileList", new FileSystemResource(new File("file.txt")));

//        doNothing().when(reportService).registerReport(reportDTO, memberList, fileList);

        // when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/reports")v
                .post("/api/v1/reports")
//                .multipart("/api/v1/reports")
//                .file(file1)
//                .file(file2)
//                .content(reportDTO)
                .header("Authorization", "Bearer " + TOKEN)
//                .contentType(MediaType.APPLICATION_JSON);
                .contentType(MediaType.MULTIPART_FORM_DATA);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        // then
//        verify(reportService, times(1)).registerReport(reportDTO, memberList, fileList);
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
