package com.chani.springbootbasicproject.file;

import java.nio.ByteBuffer;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@ConditionalOnProperty(prefix = "aws.s3", name = "enabled", havingValue = "true")
public class S3FileStorageService implements FileStorageService {

    private final S3Client s3Client;
    private final String bucket;
    private final String region;

    public S3FileStorageService(S3Client s3Client,
                                @Value("${aws.s3.bucket}") String bucket,
                                @Value("${aws.s3.region}") String region) {
        this.s3Client = s3Client;
        this.bucket = bucket;
        this.region = region;
    }

    @Override
    public String uploadImage(byte[] imageBytes, String fileName, String contentType) {
        String key = "uploads/" + UUID.randomUUID() + "-" + fileName;

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();

        s3Client.putObject(request, RequestBody.fromByteBuffer(ByteBuffer.wrap(imageBytes)));
        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;
    }
}
