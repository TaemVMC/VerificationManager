package com.verifymycoin.VerificationManager.service;

import com.verifymycoin.VerificationManager.model.entity.image.CustomImage;
import com.verifymycoin.VerificationManager.model.entity.image.CustomTextType;
import com.verifymycoin.VerificationManager.model.request.VerificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImageGenerateService {

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
}
