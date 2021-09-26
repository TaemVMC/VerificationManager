package com.verifymycoin.VerificationManager.controller;

import com.verifymycoin.VerificationManager.model.entity.Verification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "Kafka Test" })
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final KafkaTemplate<Object, Verification> kafkaTemplate;

    private static final String TOPIC = "transactionSummary";

    @ApiOperation(value = "메시지 전송", notes = "Kafka 메시지 전송")
    @PostMapping
    public String sendMessage(@RequestBody Verification verification) {

        kafkaTemplate.send(TOPIC, verification);

        log.info("kafka send verification 객체 : {}", verification);
        return "success";
    }
}