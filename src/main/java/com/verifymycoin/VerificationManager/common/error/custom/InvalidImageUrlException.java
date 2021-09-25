package com.verifymycoin.VerificationManager.common.error.custom;

import com.verifymycoin.VerificationManager.model.response.StatusEnum;

public class InvalidImageUrlException extends CustomException {
    public InvalidImageUrlException() {
        super(StatusEnum.INVALID_IMAGE_URL);
    }
}
