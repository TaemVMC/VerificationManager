package com.verifymycoin.VerificationManager.common.error.custom;

import com.verifymycoin.VerificationManager.model.response.StatusEnum;

public class NotFoundVerificationException extends CustomException {
    public NotFoundVerificationException() {
        super(StatusEnum.NOT_FOUND_VERIFICATION);
    }
}