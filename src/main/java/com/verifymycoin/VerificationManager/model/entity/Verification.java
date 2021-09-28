package com.verifymycoin.VerificationManager.model.entity;

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
@Builder
public class Verification {

    @Id
    private String id;

    private String userId;

    private String exchangeName;    // 거래소명

    private String startDate;         // 조회 시작일

    private String endDate;           // 조회 종료일

    private String orderCurrency;   // 코인

    private String paymentCurrency; // 주문통화

    private String units;           // 보유 코인 개수 (조회 종료 시점)

    private String profit;           // 수익실현금액

    private String avarageCost;     // 평단가

    private String imageUrl;
}
