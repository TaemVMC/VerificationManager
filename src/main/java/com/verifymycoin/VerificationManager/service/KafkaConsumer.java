package com.verifymycoin.VerificationManager.service;

import com.verifymycoin.VerificationManager.model.entity.Verification;
import com.verifymycoin.VerificationManager.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final ImageServiceImpl imageService;

    private final VerificationRepository verificationRepository;

    // 카프카 이벤트 처리
    @KafkaListener(topics = "transactionSummary")
    public void consume(
            @Payload Verification verificationRequest,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) throws IOException {

        log.info("event listen : {}", verificationRequest);
        imageService.saveImage(verificationRequest);
    }

    // 카프카 이벤트 처리
    @KafkaListener(topics = "userManager.signOut")
    public void signOut(
            @Payload String userId,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {

        log.info("event listen : {}", userId);
        verificationRepository.deleteAllByUserId(userId);
    }
}