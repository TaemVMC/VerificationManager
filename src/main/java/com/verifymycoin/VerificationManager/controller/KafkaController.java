package com.verifymycoin.VerificationManager.controller;

import com.verifymycoin.VerificationManager.model.request.VerificationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "Kafka Test" })
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/kafka")
public class KafkaController {

    private final KafkaTemplate<String, VerificationRequest> kafkaTemplate;

    private static final String TOPIC = "exam";

    @ApiOperation(value = "메시지 전송", notes = "Kafka 메시지 전송")
    @PostMapping("/{coinName}")
    public String sendMessage(@PathVariable("coinName") String coinName) {
        VerificationRequest verificationRequest = new VerificationRequest();
        verificationRequest.setCoinName(coinName);
        verificationRequest.setEarningRate("17.4");
        verificationRequest.setRevenue("190000");
        verificationRequest.setSold("F");

        kafkaTemplate.send(TOPIC, verificationRequest);

        System.out.println(verificationRequest);
        return "success";
    }
}