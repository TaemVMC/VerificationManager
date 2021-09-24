package com.verifymycoin.VerificationManager.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final String dirName = "verification";

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload() throws IOException {
        File uploadFile = convert();
        return upload(uploadFile);
    }

    private String upload(File uploadFile) {

        String saveFileName = UUID.randomUUID().toString();

        String fileName = dirName +"/" + saveFileName;
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile); // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl;  // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead)); // PublicRead 권한으로 업로드 됨
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("파일이 삭제되었습니다.");
        } else {
            System.out.println("파일이 삭제되지 못했습니다.");
        }
    }

    public void deletefile(String file_name) {
        System.out.println(file_name);
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, file_name));
    }

    private File convert() {
        String userDir = System.getProperty("user.dir");
        String filePath = String.format("%s/tmp.png", userDir);

        File file = new File(filePath);
        return file;
    }
}
