package com.verifymycoin.VerificationManager.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class VerificationRequest {

    private String userId;

    private String coinName;

    private Date startDate;

    private String purchaseAmount;

    private Date endDate;

    private String valuationAmount;

    private String sold;

    private String earningRate;

    private String revenue;
}
