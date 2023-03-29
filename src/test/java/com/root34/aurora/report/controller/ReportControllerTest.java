package com.root34.aurora.report.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.root34.aurora.report.service.ReportService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ReportControllerTest.class);
    private final int MEMBER_CODE = 1;
    private final String REPORT_TYPE = "Routine";
    private final char COMPLETION_STATUS = 'N';

    @Autowired
    private ReportService reportService;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Test
    public void 보고_컨트롤러_의존성_주입_테스트() {

        assertNotNull(reportService);
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
