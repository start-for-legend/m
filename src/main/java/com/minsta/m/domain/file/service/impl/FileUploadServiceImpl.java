package com.minsta.m.domain.file.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.minsta.m.domain.file.controller.data.response.FileUploadResponse;
import com.minsta.m.domain.file.exception.FileUploadFailedException;
import com.minsta.m.domain.file.exception.InvalidFormatFileException;
import com.minsta.m.domain.file.service.FileUploadService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@ServiceWithTransactional
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    @Override
    public FileUploadResponse execute(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());

        String contentType = file.getContentType();

        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentLength(file.getSize());

        objectMetadata.setContentType(contentType);

        try (InputStream inputStream = file.getInputStream()) {

            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileUploadFailedException();
        }

        String fileUrl = generateFileUrl(fileName);

        return FileUploadResponse.builder()
                .awsUrl(fileUrl)
                .build();
    }

    public void deleteFile(String fileName) {

        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private String createFileName(String fileName) {

        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {

        try {

            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {

            throw new InvalidFormatFileException();
        }
    }

    private String generateFileUrl(String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}
