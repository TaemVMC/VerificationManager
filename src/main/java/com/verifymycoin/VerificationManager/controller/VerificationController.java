package com.verifymycoin.VerificationManager.controller;

import com.verifymycoin.VerificationManager.model.response.VerificationResponse;
import com.verifymycoin.VerificationManager.model.response.StatusEnum;
import com.verifymycoin.VerificationManager.repository.VerificationRepository;
import com.verifymycoin.VerificationManager.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Api(tags = { "증명 페이지 Controller" })
@RestController
@AllArgsConstructor
@RequestMapping(value = "/verification")
public class VerificationController {

    private final VerificationRepository verificationRepository;

    private final ImageService imageService;

    @GetMapping
    @ApiOperation(value = "증명 목록", notes = "사용자의 증명 목록")
    public ResponseEntity<?> verificationList(@RequestHeader HttpHeaders header) {
        VerificationResponse response = VerificationResponse.of(StatusEnum.OK, verificationRepository.findAllByUserId(header.getFirst("userId")));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/image/{verificationId}")
    @ApiOperation(value = "증명 image url", notes = "증명 image url")
    public ResponseEntity<?> getVerificationImageUrl(@PathVariable @ApiParam(value = "해당 증명서의 id", required = true)  String verificationId) {
        Map<String, String> result = new HashMap<>();
        result.put("url", verificationRepository.findById(verificationId).get().getImageUrl());
        VerificationResponse response = VerificationResponse.of(StatusEnum.OK, result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 증명 image 다운로드
    @GetMapping("/image/download/{verificationId}")
    @ApiOperation(value = "증명 image 다운로드", notes = "증명 image url")
    public ResponseEntity<?> getVerificationImage(@PathVariable @ApiParam(value = "해당 증명서의 id", required = true) String verificationId) {
        try {
            VerificationResponse verificationResponse =  VerificationResponse.of(StatusEnum.OK, imageService.downloadImage(verificationRepository.findById(verificationId).get().getImageUrl()));
            return new ResponseEntity<>(verificationResponse, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 증명 url
    @GetMapping("/{verificationId}")
    @ApiOperation(value = "증명 image url", notes = "증명 image url")
    public ResponseEntity<?> getVerificationUrl(@PathVariable @ApiParam(value = "해당 증명서의 id", required = true)  String verificationId) {
        VerificationResponse verificationResponse = VerificationResponse.of(StatusEnum.OK, verificationRepository.findById(verificationId));
        return new ResponseEntity<>(verificationResponse, HttpStatus.OK);
    }

}
