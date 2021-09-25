package com.verifymycoin.VerificationManager.service;

import com.verifymycoin.VerificationManager.common.error.custom.InvalidImageUrlException;
import com.verifymycoin.VerificationManager.common.error.custom.NotFoundImageException;
import com.verifymycoin.VerificationManager.common.error.custom.NotFoundVerificationException;
import com.verifymycoin.VerificationManager.common.utils.IOUtil;
import com.verifymycoin.VerificationManager.model.entity.Verification;
import com.verifymycoin.VerificationManager.model.entity.image.CustomImage;
import com.verifymycoin.VerificationManager.model.entity.image.CustomTextType;
import com.verifymycoin.VerificationManager.model.request.VerificationRequest;
import com.verifymycoin.VerificationManager.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final VerificationRepository verificationRepository;
    private final S3Uploader s3Uploader;

    public void saveImage(VerificationRequest verificationRequest) throws IOException {

        Verification verification = new Verification();
        BeanUtils.copyProperties(verification, verificationRequest);

        verification.setUserId(verificationRequest.getUserId());     // userId를 어디서 얻을 것인가

        // 2. 이미지 생성 -> service 따로 빼기
        generateImage(verificationRequest);

        // 3. 이미지 s3에 저장 -> url 얻기 (워터마크 넣기 or 이미지에 하이퍼링크 넣기)
        String url = s3Uploader.upload(); // 사진 업로드
        verification.setImageUrl(url);

        log.info("verification object id : {}", verificationRepository.save(verification).getId());
    }

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

    public Map<String, String> downloadImage(String imageUrl) throws NotFoundImageException {
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
            throw new NotFoundImageException();
        } finally {
            IOUtil.close(is, os);
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("dir", outputDir + fileName);
        return resultMap;
    }

    public Map<String, String> getImageUrl(String verificationId) throws Exception {
        Map<String, String> resultMap = new HashMap<>();
        Verification verification = verificationRepository.findById(verificationId).orElseThrow(() -> new NotFoundVerificationException());

        URL url = new URL(verification.getImageUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int responseCode = conn.getResponseCode();

        // 만약 url이 잘못 되었다면 이미지 다시 생성 -> 저장
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new InvalidImageUrlException();
        }

        resultMap.put("url", verification.getImageUrl());
        return resultMap;
    }
}
