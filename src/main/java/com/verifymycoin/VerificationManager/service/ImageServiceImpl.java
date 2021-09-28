package com.verifymycoin.VerificationManager.service;

import com.verifymycoin.VerificationManager.common.error.custom.InvalidImageUrlException;
import com.verifymycoin.VerificationManager.common.error.custom.NotFoundImageException;
import com.verifymycoin.VerificationManager.common.error.custom.NotFoundVerificationException;
import com.verifymycoin.VerificationManager.common.utils.IOUtil;
import com.verifymycoin.VerificationManager.model.entity.Verification;
import com.verifymycoin.VerificationManager.model.entity.image.CustomImage;
import com.verifymycoin.VerificationManager.model.entity.image.CustomTextType;
import com.verifymycoin.VerificationManager.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final VerificationRepository verificationRepository;
    private final S3Uploader s3Uploader;

    /**
     *
     * @param verification
     * @return 사진 저장된 url 반환
     * @throws IOException
     */
    @Override
    public String saveImage(Verification verification) throws IOException {

        // 2. 이미지 생성 -> service 따로 빼기
        generateImage(verification);

        // 3. 이미지 s3에 저장 -> url 얻기 (워터마크 넣기 or 이미지에 하이퍼링크 넣기)
        String url = s3Uploader.upload(); // 사진 업로드
        verification.setImageUrl(url);


        log.info("verification object id : {}", verificationRepository.save(verification).getId());
        return url;
    }

    /**
     * text -> 사진으로 변환 후 저장
     * @param verification
     */
    @Override
    public void generateImage(Verification verification) {
//        String userDir = System.getProperty("user.dir");;
//        String filePath = String.format("%s/tmp.png", userDir);
//        log.info("생성될 파일 : {}", filePath);

        CustomImage image = CustomImage.builder()
                .imageWidth(600)
                .imageHeight(400)
                .imageColor("#C3D8E6")
                .build();

        BufferedImage resultImage = image.converting(
//                    filePath,
                    CustomTextType.title.getText(verification.getUserId() + "'s Verification"),
                    CustomTextType.subtitle.getText("코인 \t: " + verification.getOrderCurrency() + "\t" + verification.getStartDate() + " ~ " + verification.getEndDate()),
                    CustomTextType.subtitle.getText("거래소명 \t: " + verification.getExchangeName()),
                    CustomTextType.subtitle.getText("보유 코인 개수 \t: " + verification.getUnits()),
                    CustomTextType.subtitle.getText("수익 실현 금액 \t: " + verification.getProfit()),
                    CustomTextType.subtitle.getText("평단가 \t: " + verification.getAvarageCost()),
                    CustomTextType.subtitle.getText("주문 통화 \t: " + verification.getPaymentCurrency()),
                    CustomTextType.comment.getText("created by VMC")
        );
        writeWatermark(resultImage);
//        log.info("이미지 파일 생성 완료");
    }


    @Override
    public void writeWatermark(BufferedImage sourceImage){
        String strWText = "VERIFY MY COIN - VMC";
        String userDir = System.getProperty("user.dir");;
        String filePath = String.format("%s/tmp.png", userDir);
        log.info("생성될 파일 : {}", filePath);

        try {
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

            g2d.scale(1, 1);
            g2d.addRenderingHints(
                    new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON));
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
            GlyphVector fontGV = font.createGlyphVector(g2d.getFontRenderContext(), strWText);
            Rectangle size = fontGV.getPixelBounds(g2d.getFontRenderContext(), 0, 0);
            Shape textShape = fontGV.getOutline();
            double textWidth = size.getWidth();
            double textHeight = size.getHeight() * 5; // 텍스트 간격
            AffineTransform rotate45 = AffineTransform.getRotateInstance(Math.PI / 5d);
            Shape rotatedText = rotate45.createTransformedShape(textShape);

            // use a gradient that repeats 4 times
            g2d.setPaint(new GradientPaint(0, 0,
                    new Color(0f, 0f, 0f, 0.05f),
                    sourceImage.getWidth() / 2, sourceImage.getHeight() / 2,
                    new Color(0f, 0f, 0f, 0.05f)));

            g2d.setStroke(new BasicStroke(1f));

            double yStep = Math.sqrt(textWidth * textWidth / 2); //

            for (double x = -textHeight; x < sourceImage.getWidth() / 2; x += textHeight) {
                double y = -yStep;

                for (; y < sourceImage.getHeight(); y += yStep) {
                    g2d.draw(rotatedText);
                    g2d.fill(rotatedText);
                    g2d.translate(0, yStep);
                }
                g2d.translate(textHeight * 3, -(y + yStep));

            }
            try (OutputStream os = new FileOutputStream(new File(filePath))) {
                ImageIO.write(sourceImage, "png", os);
            }
            g2d.dispose();
            log.info("이미지 파일 저장 완료");

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    /**
     * 
     * @param imageUrl
     * @return 사진이 저장된 경로 반환
     * @throws NotFoundImageException
     */
    @Override
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

    /**
     * 
     * @param verificationId
     * @return verificationId에 매치되는 url 반환
     * @throws Exception
     */
    @Override
    public Map<String, String> getImageUrl(String verificationId) throws Exception {
        Map<String, String> resultMap = new HashMap<>();
        Verification verification = verificationRepository.findById(verificationId).orElseThrow(() -> new NotFoundVerificationException());

        URL url = new URL(verification.getImageUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int responseCode = conn.getResponseCode();

        // 만약 url이 잘못 되었다면 이미지 다시 생성 -> 저장 X throw Exception
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new InvalidImageUrlException();
        }

        resultMap.put("url", verification.getImageUrl());
        return resultMap;
    }
}
