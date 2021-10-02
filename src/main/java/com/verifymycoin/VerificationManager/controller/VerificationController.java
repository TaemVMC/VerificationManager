package com.verifymycoin.VerificationManager.controller;

import com.verifymycoin.VerificationManager.common.error.custom.InvalidImageUrlException;
import com.verifymycoin.VerificationManager.common.error.custom.NotFoundImageException;
import com.verifymycoin.VerificationManager.common.error.custom.NotFoundVerificationException;
import com.verifymycoin.VerificationManager.model.entity.Verification;
import com.verifymycoin.VerificationManager.model.response.VerificationResponse;
import com.verifymycoin.VerificationManager.model.response.StatusEnum;
import com.verifymycoin.VerificationManager.repository.VerificationRepository;
import com.verifymycoin.VerificationManager.service.ImageServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Api(tags = { "증명 페이지 Controller" })
@RestController
@AllArgsConstructor
@RequestMapping(value = "/verification")
public class VerificationController {

    private final VerificationRepository verificationRepository;

    private final ImageServiceImpl imageService;

    @GetMapping
    @ApiOperation(value = "증명 목록", notes = "사용자의 증명서 목록")
    public ResponseEntity<?> verificationList(@RequestHeader HttpHeaders header) {
        if (!verificationRepository.existsByUserId(header.getFirst("userId"))) {    // 해당 userId 에 해당하는 증명서가 없다면 증명서를 찾을 수 없다는 에러
            throw new NotFoundVerificationException();
        }
        VerificationResponse response = VerificationResponse.of(StatusEnum.OK, verificationRepository.findAllByUserId(header.getFirst("userId")));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/image/{verificationId}")
    @ApiOperation(value = "증명서 image url", notes = "증명서 image url")
    public ResponseEntity<?> getVerificationImageUrl(@PathVariable @ApiParam(value = "해당 증명서의 id", required = true)  String verificationId) throws Exception {
        VerificationResponse response = null;
        try {
            response = VerificationResponse.of(StatusEnum.OK, imageService.getImageUrl(verificationId));
        } catch (NotFoundVerificationException e) {
            throw new NotFoundVerificationException();
        } catch (InvalidImageUrlException e) {
            throw new InvalidImageUrlException();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 증명 image 다운로드
    @GetMapping("/image/download/{verificationId}")
    @ApiOperation(value = "증명서 image 다운로드", notes = "증명서 image 다운로드")
    public ResponseEntity<?> getVerificationImage(@PathVariable @ApiParam(value = "해당 증명서의 id", required = true) String verificationId) {
        try {
            VerificationResponse verificationResponse =  VerificationResponse.of(StatusEnum.OK, imageService.downloadImage(verificationRepository.findById(verificationId).get().getImageUrl()));
            return new ResponseEntity<>(verificationResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundImageException();
        }
    }

    // 증명 url
    @GetMapping("/{verificationId}")
    @ApiOperation(value = "증명서 url(상세페이지)", notes = "증명서 url(상세페이지)")
    public ResponseEntity<?> getVerificationUrl(@PathVariable @ApiParam(value = "해당 증명서의 id", required = true)  String verificationId) {
        VerificationResponse verificationResponse = VerificationResponse.of(StatusEnum.OK, verificationRepository.findById(verificationId));
        return new ResponseEntity<>(verificationResponse, HttpStatus.OK);
    }

    // 이미지 다시 생성
    @PostMapping("/image")
    @ApiOperation(value = "증명 image 재생성", notes = "증명 image url이 잘못되었을 경우 다시 생성하기 위함")
    public ResponseEntity<?> resaveImageUrl(@RequestBody @ApiParam(value = "증명서 정보", required = true) Verification verification) {
        VerificationResponse verificationResponse = null;
        try {
            verificationResponse = VerificationResponse.of(StatusEnum.OK, imageService.saveImage(verification));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(verificationResponse, HttpStatus.OK);
    }
}