package com.jonrodriguez.qrcode.aws.s3.docker.insfrastructure;

import com.jonrodriguez.qrcode.aws.s3.docker.ports.StoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetBucketLocationRequest;
import software.amazon.awssdk.services.s3.model.GetBucketLocationResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class S3StorageAdapter implements StoragePort {

    private final S3Client s3Client;
    private final String bucketName;
    private final String region;

    public S3StorageAdapter(
            @Value("${aws.s3.bucket-name}") String bucketName,
            @Value("${aws.s3.region}") String region) {

        this.bucketName = bucketName;
        this.region = region;

        System.out.println("AWS region lida: " + region);
        System.out.println("AWS bucket lido: " + bucketName);

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .build();

        try {
            GetBucketLocationResponse response = s3Client.getBucketLocation(
                    GetBucketLocationRequest.builder().bucket(bucketName).build()
            );
            System.out.println("Bucket real region: " + response.locationConstraintAsString());
        } catch (Exception e) {
            System.out.println("Erro ao validar bucket: " + e.getMessage());
        }
    }

    @Override
    public String upload(byte[] fileData, String fileName, String contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(contentType)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileData));

        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
    }
}