package com.suiteonix.nix.User.service;

import com.suiteonix.nix.Storage.NixImage;
import com.suiteonix.nix.spi.location.HomeAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Builder
@Schema(name = "User.Create")
public record UserCreateDto(
        String firstname,
        String lastname,
        String email,
        String phone,
        LocalDate dateOfBirth,
        NixImage avatar,
        String bio,
        HomeAddress.Create address
) {

    public record WithAvatar(
            UserCreateDto user,
            MultipartFile avatar
    ) {
    }
}
