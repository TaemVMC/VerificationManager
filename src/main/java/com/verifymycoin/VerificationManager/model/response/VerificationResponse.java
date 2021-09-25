package com.verifymycoin.VerificationManager.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerificationResponse {

    private int status;
    private String message;
    private Object data;

    public VerificationResponse(StatusEnum code, Object data) {
        this.status = code.getStatus();
        this.message = code.getMessage();
        this.data = data;
    }

    public static VerificationResponse of(StatusEnum code, Object data) {
        return new VerificationResponse(code, data);
    }
}