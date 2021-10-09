package com.verifymycoin.VerificationManager.common.error;

import com.verifymycoin.VerificationManager.common.error.custom.*;
import com.verifymycoin.VerificationManager.model.response.StatusEnum;
import com.verifymycoin.VerificationManager.model.response.VerificationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<VerificationResponse> handleException(Exception e) {
        VerificationResponse response = VerificationResponse.of(StatusEnum.INTERNAL_SERER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NotFoundImageException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<VerificationResponse> handleNotFoundImageException(NotFoundImageException e) {
        VerificationResponse response = VerificationResponse.of(StatusEnum.NOT_FOUND_IMAGE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundVerificationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<VerificationResponse> handleNotFoundVerificationException(NotFoundVerificationException e) {
        VerificationResponse response = VerificationResponse.of(StatusEnum.NOT_FOUND_VERIFICATION);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundUserException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<VerificationResponse> handleNotFoundUserException(NotFoundUserException e) {
        VerificationResponse response = VerificationResponse.of(StatusEnum.NOT_FOUND_USER);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidImageUrlException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<VerificationResponse> handleInvalidImageUrlException(InvalidImageUrlException e) {
        VerificationResponse response = VerificationResponse.of(StatusEnum.INVALID_IMAGE_URL);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundVerificationPublicException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<VerificationResponse> handleNotFoundVerificationPublicException(NotFoundVerificationPublicException e) {
        VerificationResponse response = VerificationResponse.of(StatusEnum.NOT_FOUND_PUBLIC_VERIFICATION);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}