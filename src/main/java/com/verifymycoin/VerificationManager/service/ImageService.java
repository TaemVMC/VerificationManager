package com.verifymycoin.VerificationManager.service;

import com.verifymycoin.VerificationManager.common.utils.IOUtil;
import com.verifymycoin.VerificationManager.model.entity.image.CustomImage;
import com.verifymycoin.VerificationManager.model.entity.image.CustomTextType;
import com.verifymycoin.VerificationManager.model.request.VerificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    public void generateImage(VerificationRequest verificationRequest) {
        String userDir = System.getProperty("user.dir");;
        String filePath = String.format("%s/tmp.png", userDir);
        log.info("생성될 파일 : {}", filePath);

        CustomImage image = CustomImage.builder()
                .imageWidth(600)
                .imageHeight(400)
                .imageColor("#C3D8E6")
                .build();

        image.converting(
                filePath,
                CustomTextType.title.getText(verificationRequest.getUserId() + "'s Verification"),
                CustomTextType.subtitle.getText("coin : " + verificationRequest.getCoinName()),
                CustomTextType.content.getText("java로 text를 image로 변환하기"),
                CustomTextType.comment.getText("created by VMC")
        );

        log.info("이미지 파일 생성 완료");
    }

    public Map<String, String> downloadImage(String imageUrl) throws Exception {
        String outputDir = "D:/vmc/";
        String fileName = IOUtil.getDateFormat() + ".png";
        InputStream is = null;
        FileOutputStream os = null;

        try{
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();

            IOUtil.createDir(outputDir);
            os = new FileOutputStream(new File(outputDir, fileName));
            IOUtil.writeFile(is, os);

            conn.disconnect();
            log.info("이미지 파일 다운로드 완료");
        } catch (Exception e) {
            log.error("An error occurred while trying to download a file.");
            throw new Exception("An error occurred while trying to download a file.");
        } finally {
            IOUtil.close(is, os);
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("dir", outputDir + fileName);
        return resultMap;
    }
}
