package com.verifymycoin.VerificationManager.common.error.custom;

import com.verifymycoin.VerificationManager.model.response.StatusEnum;

public class NotFoundImageException extends CustomException {
    public NotFoundImageException() {
        super(StatusEnum.NOT_FOUND_IMAGE);
    }
}