package com.verifymycoin.VerificationManager.service;

import com.verifymycoin.VerificationManager.model.entity.Verification;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageService {
    String saveImage(Verification verification) throws IOException;

    void generateImage(Verification verification);

    void writeWatermark(BufferedImage sourceImage);

}
