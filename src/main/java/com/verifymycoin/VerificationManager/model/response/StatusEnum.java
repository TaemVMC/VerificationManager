package com.verifymycoin.VerificationManager.model.response;

import lombok.Getter;

@Getter
public enum StatusEnum {

    OK(3200, "SUCCESS"),
    BAD_REQUEST(3400, "BAD_REQUEST"),
    NOT_FOUND(3404, "NOT_FOUND"),
    INTERNAL_SERER_ERROR(3500, "INTERNAL_SERVER_ERROR"),

    // Common
    METHOD_NOT_ALLOWED(3405, "METHOD_NOT_ALLOWED"),
    HANDLE_ACCESS_DENIED(3403, "HANDLE_ACCESS_DENIED"),

    // Verification
    NOT_FOUND_IMAGE(3601, "NOT_FOUND_IMAGE"),
    NOT_FOUND_VERIFICATION(3602, "NOT_FOUND_VERIFICATION"),
    NOT_FOUND_USER(3603, "NOT_FOUND_USER"),
    INVALID_IMAGE_URL(3604, "INVALID_IMAGE_URL"),

    // Public
    NOT_FOUND_PUBLIC_VERIFICATION(404, "NOT_FOUND_VERIFICATION");

    private int code;
    private final String message;

    StatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}