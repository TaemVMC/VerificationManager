package com.verifymycoin.VerificationManager.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerificationResponse {

    private int code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public VerificationResponse(StatusEnum code, Object data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    public VerificationResponse(StatusEnum code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }


    public static VerificationResponse of(StatusEnum code, Object data) {
        return new VerificationResponse(code, data);
    }

    public static VerificationResponse of(StatusEnum errorCode) {
        return new VerificationResponse(errorCode);
    }
}