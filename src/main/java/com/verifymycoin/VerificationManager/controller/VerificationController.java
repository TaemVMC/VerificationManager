package com.verifymycoin.VerificationManager.controller;

import com.verifymycoin.VerificationManager.Config.S3Uploader;
import com.verifymycoin.VerificationManager.model.entity.Verification;
import com.verifymycoin.VerificationManager.repository.VerificationRepository;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@AllArgsConstructor
public class VerificationController {

    private final VerificationRepository verificationRepository;

    private final S3Uploader s3Uploader;

    @GetMapping("/verification")
    @ApiOperation(value = "증명 목록", notes = "사용자의 증명 목록")
    public List<Verification> verificationList() {
        // jwt header의 userId
        String userId = null;

        return verificationRepository.findAllByUserId(userId);
    }

    @PostMapping("/verification")
    @ApiOperation(value = "증명 목록 저장", notes = "사용자의 증명 목록 저장")
    public String saveVerificationList(@RequestBody List<Verification> verification) {
        return verificationRepository.saveAll(verification).get(0).getId();
    }

//
    @PostMapping("/verification/image/{verificationId}")
    @ApiOperation(value = "image 업로드 및 수정", notes = "사용자 증명 image 업로드")
    @Transactional
    public Map<String, String> uploadFile(@RequestParam(value = "file", required = true) MultipartFile file,
                                          @PathVariable String verificationId) throws IOException {

        try {
            System.out.println(file.getSize());
            String originalFilename = file.getOriginalFilename();
            String saveFileName = UUID.randomUUID().toString()
                    + originalFilename.substring(originalFilename.lastIndexOf('.'));

            String s3Path = "profile";
            String url = s3Uploader.upload(file, s3Path, saveFileName); // 사진 업로드

            Optional<Verification> verification = verificationRepository.findById(verificationId);
            if (!verification.isPresent()) {
                s3Uploader.deletefile(verification.get().getImageUrl());
            }

//            throw new RestException(HttpStatus.NO_CONTENT, "증명 정보를 찾지 못했습니다.");
            verification.get().setImageUrl(url);
            verificationRepository.save(verification.get());

            Map<String, String> result = new HashMap<>();
            result.put("url", url);// 사진 url 리턴
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/verification/image/{verificationId}")
    @ApiOperation(value = "증명 image 불러오기", notes = "사용자의 증명 image 저장")
    public String getVerificationImage(@PathVariable String verificationId) {
        return verificationRepository.findById(verificationId).get().getImageUrl();
    }

//    @PostMapping("/verification/{verificationId}")
//    @ApiOperation(value = "증명 url", notes = "사용자의 증명 url")
//    public String makeTest(@RequestParam String verificationId) {
//        Optional<Verification> verification = verificationRepository.findById(verificationId);
//        verification.get().setUrl(verificationId);
//        return verificationRepository.save(verification.get()).getUrl();
//    }
//
//    @GetMapping("/verification")
//    @ApiOperation(value = "증명 url", notes = "사용자의 증명 url")
//    public String makeTest(@RequestBody List<Verification> verification) {
//        return verificationRepository.saveAll(verification).get(0).getId();
//    }
}
