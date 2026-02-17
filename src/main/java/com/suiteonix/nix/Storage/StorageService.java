package com.suiteonix.nix.Storage;

import com.suiteonix.nix.shared.CustomContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface StorageService {

    String bucketName();

    void delete(String fileUrl);

    String upload(MultipartFile file, String directory, String name);

    default String upload(MultipartFile file, String directory) {
        return upload(file, directory, null);
    }

    default <T extends IFile> T upload(T iFile, MultipartFile file, String directory, String name) {
        if (iFile == null) return null;
        iFile.delete();
        iFile.setUrl(upload(file, directory, name));
        return iFile;
    }

    InputStream get(String fileUrl);

    default void upload(IFile iFile, MultipartFile file, String directory) {
        if (iFile == null) return;
        iFile.delete();
        iFile.setUrl(upload(file, directory));
    }

    default void delete(IFile iFile) {
        if (iFile.hasFile()) delete(iFile.getUrl());
        iFile.setUrl(null);
    }

    default InputStream download(IFile iFile) {
        if (iFile.getUrl() == null) return null;
        return get(iFile.getUrl());
    }

    default String extractObjectNameFromUrl(String fileUrl) {
        // Extract the path after the bucket name
        int bucketIndex = fileUrl.indexOf(bucketName());
        if (bucketIndex != -1) {
            String path = fileUrl.substring(bucketIndex + bucketName().length() + 1);
            // Remove query parameters if any
            int queryIndex = path.indexOf('?');
            if (queryIndex != -1) {
                path = path.substring(0, queryIndex);
            }
            return path;
        }
        // If the URL format is different, try to extract the last part of the path
        String[] parts = fileUrl.split("/");
        if (parts.length >= 2) {
            return parts[parts.length - 2] + "/" + parts[parts.length - 1].split("\\?")[0];
        }
        throw new IllegalArgumentException("Invalid file URL format: " + fileUrl);
    }

    static StorageService GET() {
        return CustomContextHolder.context().getBean(StorageService.class);
    }
}
