package com.twelvegg.aicc.s3.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@RequiredArgsConstructor
@Service
public class S3ServiceImpl implements S3Service {
    private final AmazonS3Client s3Client;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    @Override
    public String upload(MultipartFile file, String dirName) {
        if (file.isEmpty())
            throw new CustomException(ErrorCode.INVALID_FILE);
        if (!file.getOriginalFilename().endsWith(".wav"))
            throw new CustomException(ErrorCode.INVALID_FILE);
        String fileName = dirName + "/" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            s3Client.putObject(
                    new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata)
                            .withCannedAcl(CannedAccessControlList.Private));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INVALID_FILE);
        }

        return s3Client.getUrl(bucketName, fileName).toString();
    }

}
