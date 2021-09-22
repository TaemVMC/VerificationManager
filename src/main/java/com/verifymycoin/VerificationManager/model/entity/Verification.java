package com.verifymycoin.VerificationManager.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "verification")
@Data
public class Verification {

    @Id
    private String id;

    private String userId;

    private String coinName;

    private Date startDate;

    private String purchaseAmount;

    private Date endDate;

    private String valuationAmount;

    private String sold;

    private String earningRate;

    private String revenue;

    private String imageUrl;

    @Builder
    public Verification(String userId, String coinName, Date startDate, String purchaseAmount) {
        this.userId = userId;
        this.coinName = coinName;
        this.startDate = startDate;
        this.purchaseAmount = purchaseAmount;
    }

}
