package com.suiteonix.db.nix.Storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Lob;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public interface IFile {

    @Lob
    String getUrl();

    void setUrl(String newUrl);

    @Transient
    @JsonIgnore
    default String getFileName() {
        return null;
    }

    @Transient
    @JsonIgnore
    default boolean hasFile() {
        return getUrl() != null && !getUrl().isEmpty();
    }

    @Transient
    @JsonIgnore
    default boolean isEmpty() {
        return !hasFile();
    }

    @Transient
    @JsonIgnore
    default boolean notEmpty() {
        return hasFile();
    }

    default void upload(MultipartFile file, String directory) {
        StorageService.GET().upload(this, file, directory, getFileName());
    }

    default void upload(MultipartFile file, String directory, String name) {
        StorageService.GET().upload(this, file, directory, name);
    }

    @Transient
    @JsonIgnore
    default void delete() {
        StorageService.GET().delete(this);
    }

    @Transient
    @JsonIgnore
    default InputStream get() {
        try (var file = StorageService.GET().download(this)) {
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    @Transient
    @JsonIgnore
    default String objectName() {
        if (getUrl() == null || getUrl().isEmpty()) return null;
        return StorageService.GET().extractObjectNameFromUrl(getUrl());
    }

    @Transient
    @JsonIgnore
    default File getFile() {
        var stream = get();
        if (stream == null) return null;
        try {
            Path tempFile = Files.createTempFile("nix-file-", ".tmp");
            Files.copy(stream, tempFile);
            return tempFile.toFile();
        } catch (IOException e) {
            return null;
        }
    }
}
