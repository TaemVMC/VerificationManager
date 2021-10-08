package com.verifymycoin.VerificationManager.model.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "verifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public class Verification {

    @Id
    private String id;

    private String userId;          // 유저 ID

    private String exchangeName;    // 거래소명

    private String endDate;         // 조회 종료일

    private String orderCurrency;   // 코인

    private String paymentCurrency; // 주문통화

    private String profit;           // 수익실현금액

    private String yield;           // 수익률

    private String imageUrl;        // 이미지 썸네일 url

    private String imageDownloadUrl;    // 이미지 다운로드 url
}
