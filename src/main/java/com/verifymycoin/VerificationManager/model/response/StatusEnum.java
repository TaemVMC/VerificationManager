package com.verifymycoin.VerificationManager.model.response;

import lombok.Getter;

@Getter
public enum StatusEnum {

    OK(200, "SUCCESS"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR"),

    // Common
    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED"),
    HANDLE_ACCESS_DENIED(403, "HANDLE_ACCESS_DENIED"),

    // Verification
    NOT_FOUND_IMAGE(400, "NOT_FOUND_IMAGE"),
    NOT_FOUND_VERIFICATION(400, "NOT_FOUND_VERIFICATION"),
    NOT_FOUND_USER(400, "NOT_FOUND_USER"),
    INVALID_IMAGE_URL(400, "INVALID_IMAGE_URL");

    private int status;
    private final String message;

    StatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }
}