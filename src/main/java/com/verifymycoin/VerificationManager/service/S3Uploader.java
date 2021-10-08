package com.verifymycoin.VerificationManager.service;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RequiredArgsConstructor
@Component
@Slf4j
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final String BUCKETDIR = "verification";

    public List<String> upload() throws IOException {
        String userDir = System.getProperty("user.dir");
        String filePath = String.format("%s/tmp.png", userDir);

        File file = new File(filePath);
        List<String> list = new ArrayList<>();
        String saveFileName = UUID.randomUUID().toString();

        String fileName = BUCKETDIR +"/" + saveFileName;
        list.add(putS3(file, fileName + ".jpg"));
        list.add(putDownS3(file, fileName + "_down.jpg"));
        removeNewFile(file);

        return list;  // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead)); // PublicRead 권한으로 업로드 됨
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private String putDownS3(File file, String fileName) throws IOException {
        FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
        try {
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
        } catch (IOException ex) {
            log.debug("IOException");
        }
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/*");
        metadata.setContentLength(file.length());

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead)); // PublicRead 권한으로 업로드 됨
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        targetFile.delete();
    }

    public void deletefile(String file_name) {
        log.info("bucket deleteFile : {}", file_name);
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, file_name));
    }
}
