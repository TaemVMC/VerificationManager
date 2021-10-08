package com.verifymycoin.VerificationManager.service;

import com.verifymycoin.VerificationManager.common.error.custom.NotFoundImageException;
import com.verifymycoin.VerificationManager.model.entity.Verification;
import org.springframework.http.ResponseEntity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public interface ImageService {
    String saveImage(Verification verification) throws IOException;

    void generateImage(Verification verification);

    void writeWatermark(BufferedImage sourceImage);

//    Map<String, String> getImageUrl(String verificationId) throws Exception;
}
