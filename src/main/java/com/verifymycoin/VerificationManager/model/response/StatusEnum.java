package com.verifymycoin.VerificationManager.model.response;

import lombok.Getter;

@Getter
public enum StatusEnum {

    OK(200, "S001","SUCCESS"),
    BAD_REQUEST(400, "S002", "BAD_REQUEST"),
    NOT_FOUND(404, "S003", "NOT_FOUND"),
    INTERNAL_SERER_ERROR(500, "S004", "INTERNAL_SERVER_ERROR"),

    // Common
    METHOD_NOT_ALLOWED(405, "C001", "METHOD_NOT_ALLOWED"),
    HANDLE_ACCESS_DENIED(403, "C002", "HANDLE_ACCESS_DENIED"),

    // Verification
    NOT_FOUND_IMAGE(400, "V001", "NOT_FOUND_IMAGE"),
    NOT_FOUNT_VERIFICATION(400, "V002", "NOT_FOUNT_VERIFICATION");


    private int status;
    private final String code;
    private final String message;

    StatusEnum(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}