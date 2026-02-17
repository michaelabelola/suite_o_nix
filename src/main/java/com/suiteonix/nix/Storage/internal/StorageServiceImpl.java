package com.suiteonix.nix.Storage.internal;

import com.suiteonix.nix.Storage.StorageService;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.utils.TransactionUtils;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Lazy
@Service
@RequiredArgsConstructor
class StorageServiceImpl implements StorageService {

    @Value("${minio.bucket}")
    private String bucketName;
    private final MinioClient minioClient;

    public String bucketName() {
        return bucketName;
    }

    @PostConstruct
    void init() {
        String policyPublic = """
                {
                  "Version": "2012-10-17",
                  "Statement": [
                    {
                      "Effect": "Allow",
                      "Principal": {"AWS": ["*"]},
                      "Action": ["s3:GetObject"],
                      "Resource": ["arn:aws:s3:::%s/*"]
                    }
                  ]
                }
                """.formatted(bucketName());
        try {
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucketName())
                            .config(policyPublic)
                            .build()
            );
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }


    public String upload(MultipartFile file, String directory, String name) {
        try {
            // Generate a unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ?
                    originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String filename =
                    (name != null && !name.isEmpty() ? name : UUID.randomUUID())
                            + extension;

            // Create the full object path
            String objectName = directory + "/" + filename;

            // Store file content and metadata for deferred upload
            final byte[] fileContent = file.getBytes();
            final String contentType = file.getContentType();

            // Defer the actual upload until after transaction commits
            TransactionUtils.executeAfterCommit(() -> {
                try {
                    // Upload the file to MinIO
                    minioClient.putObject(
                            PutObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(objectName)
                                    .stream(new ByteArrayInputStream(fileContent), fileContent.length, -1)
                                    .contentType(contentType)
                                    .build()
                    );
                } catch (Exception e) {
                    // Log the error since we're in an async context
                    System.err.println("Failed to upload file after transaction commit: " + e.getMessage());
                }
            });

            // Generate a presigned URL for the uploaded file
            return getRawFileUrl(objectName);
        } catch (Exception e) {
            throw EX.badRequest("Failed to upload file: " + e.getMessage());
        }
    }

    public String update(MultipartFile file, String oldFileUrl) {
        // Validate that the file is a type

        try {
            // Extract the object name from the URL
            String objectName = extractObjectNameFromUrl(oldFileUrl);

            // Store file content and metadata for deferred upload
            final byte[] fileContent = file.getBytes();
            final String contentType = file.getContentType();

            // Defer the actual update until after transaction commits
            TransactionUtils.executeAfterCommit(() -> {
                try {
                    // Delete the old file
                    minioClient.removeObject(
                            RemoveObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(objectName)
                                    .build()
                    );

                    // Upload the new file with the same object name
                    minioClient.putObject(
                            PutObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(objectName)
                                    .stream(new ByteArrayInputStream(fileContent), fileContent.length, -1)
                                    .contentType(contentType)
                                    .build()
                    );
                } catch (Exception e) {
                    // Log the error since we're in an async context
                    System.err.println("Failed to update file after transaction commit: " + e.getMessage());
                }
            });

            // Generate a presigned URL for the updated file
            return getRawFileUrl(objectName);
        } catch (Exception e) {
            throw EX.badRequest("UPDATE_FILE_ERROR", "Failed to update type: " + e.getMessage());
        }
    }

    public void delete(String fileUrl) {
        try {
            // Extract the object name from the URL
            String objectName = extractObjectNameFromUrl(fileUrl);

            // Defer the actual deletion until after transaction commits
            TransactionUtils.executeAfterCommit(() -> {
                try {
                    // Delete the file
                    minioClient.removeObject(
                            RemoveObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(objectName)
                                    .build()
                    );
                } catch (Exception e) {
                    // Log the error since we're in an async context
                    System.err.println("Failed to delete file after transaction commit: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            throw EX.badRequest("FILE_DELETE_ERROR", "Failed to delete file: " + e.getMessage());
        }
    }

    public InputStream get(String fileUrl) {
        try {
            // Extract the object name from the URL
            String objectName = extractObjectNameFromUrl(fileUrl);

            // Get the file
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw EX.notFound("FILE_NOT_FOUND", "Failed to get file: " + e.getMessage());
        }
    }

    private String getRawFileUrl(String objectName) {
        return getFileUrl(objectName).split("\\?")[0];
    }

    private String getFileUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .method(Method.GET)
                            .expiry(7, TimeUnit.DAYS)
                            .build()
            );
        } catch (Exception e) {
            throw EX.badRequest("FILE_URL_GENERATION_ERROR", "Failed to generate file URL: " + e.getMessage());
        }
    }
}
