package com.verifymycoin.VerificationManager.controller;

import com.verifymycoin.VerificationManager.model.entity.Verification;
import com.verifymycoin.VerificationManager.model.response.Message;
import com.verifymycoin.VerificationManager.model.response.ResponseEntity;
import com.verifymycoin.VerificationManager.model.response.StatusEnum;
import com.verifymycoin.VerificationManager.repository.VerificationRepository;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
public class VerificationController {

    private final VerificationRepository verificationRepository;

    @GetMapping("/verification")
    @ApiOperation(value = "증명 목록", notes = "사용자의 증명 목록")
    public ResponseEntity<?> verificationList(@RequestHeader HttpHeaders header) {
        // jwt header의 userId
        String userId = header.getFirst("userId");

        Message message = new Message();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setData(verificationRepository.findAllByUserId(userId));
        message.setMessage("verification list");

        return new ResponseEntity<>(message, header, HttpStatus.OK);
    }

    @GetMapping("/verification/image")
    @ApiOperation(value = "증명 image url", notes = "증명 image url")
    public String getVerificationImageUrl(@RequestHeader HttpHeaders header) {
        // jwt header의 userId
        String userId = header.getFirst("userId");
        String url = "";
        return url;
    }

    // 증명 image 다운로드


    // 증명 url


//    @PostMapping("/verification")
//    @ApiOperation(value = "증명 목록 저장", notes = "사용자의 증명 목록 저장")
//    public String saveVerificationList(@RequestHeader HttpHeaders header,
//                                       @RequestBody Verification verification) throws IOException {
//
//        // 1. 증명목록
//        verification.setUserId(header.getFirst("userId"));
//
//        // 2. 이미지 생성 -> service 따로 빼기
//        String userDir = System.getProperty("user.dir");;
//        String filePath = String.format("%s/tmp.png", userDir);
//        log.info("생성될 파일 : {}", filePath);
//
//        CustomImage image = CustomImage.builder()
//                .imageWidth(600)
//                .imageHeight(400)
//                .imageColor("#C3D8E6")
//                .build();
//
//        image.converting(
//                filePath,
//                CustomTextType.title.getText(header.getFirst("userId") + "'s Verification"),
//                CustomTextType.subtitle.getText("coin : " + verification.getCoinName()),
//                CustomTextType.content.getText("java로 text를 image로 변환하기"),
//                CustomTextType.comment.getText("created by VMC")
//        );
//
//        log.info("파일 생성 완료");
//
//        // 3. 이미지 s3에 저장 -> url 얻기 (워터마크 넣기 or 이미지에 하이퍼링크 넣기)
//        String s3Path = "verification";
//        String url = s3Uploader.upload(s3Path, "originName"); // 사진 업로드
//
//        verification.setImageUrl(url);
//
//        // 4. 증명 url 생성
//
//        return verificationRepository.save(verification).getId();
//    }

//    @GetMapping("/verification")
//    @ApiOperation(value = "증명 url", notes = "사용자의 증명 url")
//    public String makeTest(@RequestBody List<Verification> verification) {
//        return verificationRepository.saveAll(verification).get(0).getId();
//    }
}
