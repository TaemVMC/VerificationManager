package com.verifymycoin.VerificationManager.service;

import com.verifymycoin.VerificationManager.model.entity.User;
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
            @Payload Verification verificationRequest) {

        log.info("event listen : {}", verificationRequest);
        try {
            imageService.saveImage(verificationRequest);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }

    // 카프카 이벤트 처리
    @KafkaListener(topics = "userManager.signOut", containerFactory = "userKafkaListenerFactory")
    public void signOut(
            @Payload User user) {

        log.info("event listen : {}", user);
        verificationRepository.deleteAllByUserId(user.getUserId());
    }
}