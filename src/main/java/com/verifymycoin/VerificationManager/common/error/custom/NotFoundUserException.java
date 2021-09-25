package com.verifymycoin.VerificationManager.common.error.custom;

import com.verifymycoin.VerificationManager.model.response.StatusEnum;

public class NotFoundUserException extends CustomException {
    public NotFoundUserException() {
        super(StatusEnum.NOT_FOUND_USER);
    }
}
