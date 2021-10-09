package com.verifymycoin.VerificationManager.common.error.custom;

import com.verifymycoin.VerificationManager.model.response.StatusEnum;

public class NotFoundVerificationPublicException extends CustomException {
    public NotFoundVerificationPublicException() {
        super(StatusEnum.NOT_FOUND_PUBLIC_VERIFICATION);
    }
}