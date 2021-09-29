package com.verifymycoin.VerificationManager.controller;

import com.google.gson.Gson;
import com.verifymycoin.VerificationManager.model.entity.Verification;
import com.verifymycoin.VerificationManager.repository.VerificationRepository;
import com.verifymycoin.VerificationManager.service.ImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = VerificationController.class)
@DisplayName("VerificationController Test")
class VerificationControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean // Bean은 Spring 객체를 의미
//    private ImageServiceImpl imageService;
//
//    @MockBean
//    private VerificationRepository verificationRepository;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(
//                        new VerificationController(verificationRepository, imageService)
//                )
//                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // utf-8 필터 추가
//                .build();
//    }
//
//    Verification verification =
//            Verification.builder()
//                    .id("615309291fc2f34655124587")
//                    .userId("test")
//                    .exchangeName("Bithumb")
//                    .startDate("2021-01-01")
//                    .endDate("2021-01-10")
//                    .orderCurrency("OMG")
//                    .paymentCurrency("KRW")
//                    .units("10.55")
//                    .profit("-10000")
//                    .avarageCost("17.000")
//                    .build();
//
//    @Test
//    @DisplayName("사용자의 증명서 목록")
//    void verificationList() throws Exception {
//        // given
//        List<Verification> verifications = new ArrayList<>();
//        verifications.add(verification);
//
//        Optional<List<Verification>> op = Optional.of(verifications);
//        System.out.println(op.get());
//        given(verificationRepository.findAllByUserId(any()))
//                .willReturn(op);
//        given(verificationRepository.existsByUserId("test"))
//                .willReturn(true);
//
//        // when -> then
//        mockMvc.perform( // 호출 결과값이 OK가 나오면 정상처리
//                get("/verification")               // controller의 /verifications URI를 get방식으로 호출
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("userId", "test")
//                        .content("{}")
//        )
//        .andDo(print())
//        .andExpect(status().isOk());
//
//        verify(verificationRepository).findAllByUserId("test");
//        verify(verificationRepository).existsByUserId("test");
//    }
//
//    @Test
//    @DisplayName("증명서 image url")
//    void getVerificationImageUrl() throws Exception {
//        // given
//        String id = "615309291fc2f34655124587";
//        Map<String, String> map = new HashMap<>();
//        map.put("url", "https://vmc-bucket.s3.ap-northeast-2.amazonaws.com/verification/dec34c6b-b5b4-4759-acba-41028f8fca80");
//        given(imageService.getImageUrl(id))
//                .willReturn(map);
//
//        // when -> then
//        mockMvc.perform(
//                get("/verification/image", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("userId", "test")
//                        .content("{}")
//        )
//        .andExpect(status().isOk())
//        .andDo(print())
//        .andReturn();
//    }
//
//    @Test
//    @DisplayName("증명서 image 다운로드")
//    void getVerificationImage() throws Exception {
//        // given
//        String id = "615309291fc2f34655124587";
//
//        Optional<Verification> op = Optional.of(verification);
//        given(verificationRepository.findById("615309291fc2f34655124587"))
//                .willReturn(op);
//
//        Map<String, String> resultMap = new HashMap<>();
//        resultMap.put("dir", "D:/vmc/VMC_21-09-29T012616.png");
//        given(imageService.downloadImage(id))
//                .willReturn(resultMap);
//
//        // when -> then
//        mockMvc.perform(
//                        get("/verification/image/download", id)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .header("userId", "test")
//                                .content("{}")
//                )
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//    }
//
//    @Test
//    @DisplayName("증명서 url(상세페이지)")
//    void getVerificationUrl() throws Exception {
//        // given
//        String id = "615309291fc2f34655124587";
//
//        Optional<Verification> op = Optional.of(verification);
//        given(verificationRepository.findById("615309291fc2f34655124587"))
//                .willReturn(op);
//
//        // when -> then
//        mockMvc.perform(
//                        get("/verification/615309291fc2f34655124587")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .header("userId", "test")
//                                .content("{}")
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value("3200"))
//                .andExpect(jsonPath("$.data.id").value("615309291fc2f34655124587"))
//                .andExpect(jsonPath("$.data.exchangeName").value("Bithumb"))
//                .andExpect(jsonPath("$.data.orderCurrency").value("OMG"))
//                .andDo(print())
//                .andReturn();
//    }
//
//    @Test
//    @DisplayName("증명 image 재생성")
//    void resaveImageUrl() throws Exception {
//        Map<String, String> resultMap = new HashMap<>();
//        resultMap.put("dir", "D:/vmc/VMC_21-09-29T012616.png");
//        given(imageService.saveImage(verification))
//                .willReturn("https://vmc-bucket.s3.ap-northeast-2.amazonaws.com/verification/dfffb75a-6776-4805-afd3-d0b5aed1f2f4");
//
//        Gson gson = new Gson();
//        String content = gson.toJson(verification);
//
//        // when -> then
//        final ResultActions actions =
//                mockMvc.perform(
//                        post("/verification/image")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .header("userId", "test")
//                                .content(content)
//                );
//
//        // then
//        actions
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//    }
}