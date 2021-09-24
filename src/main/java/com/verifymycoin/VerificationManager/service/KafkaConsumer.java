package com.verifymycoin.VerificationManager.service;

import com.verifymycoin.VerificationManager.model.entity.Verification;
import com.verifymycoin.VerificationManager.model.request.VerificationRequest;
import com.verifymycoin.VerificationManager.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final VerificationRepository verificationRepository;

    private final S3Uploader s3Uploader;

    private final ImageGenerateService imageGenerateService;

    // 카프카 이벤트 처리
    @KafkaListener(topics = "exam", groupId = "foo")
    public void consume(
            @Payload VerificationRequest verificationRequest,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) throws IOException {

        Verification verification = new Verification();
        BeanUtils.copyProperties(verification, verificationRequest);

        verification.setUserId(verificationRequest.getUserId());     // userId를 어디서 얻을 것인가

        // 2. 이미지 생성 -> service 따로 빼기
        imageGenerateService.generateImage(verificationRequest);

        // 3. 이미지 s3에 저장 -> url 얻기 (워터마크 넣기 or 이미지에 하이퍼링크 넣기)
        String url = s3Uploader.upload(); // 사진 업로드
        verification.setImageUrl(url);

        // 4. 증명 url 생성


        log.info("verification object id : {}", verificationRepository.save(verification).getId());
    }
}