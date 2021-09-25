package com.verifymycoin.VerificationManager.common.error.custom;

import com.verifymycoin.VerificationManager.model.response.StatusEnum;

public class CustomException  extends RuntimeException {
    private StatusEnum errorCode;

    public CustomException(StatusEnum errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public StatusEnum getErrorCode() {
        return errorCode;
    }
}
