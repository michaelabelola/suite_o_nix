package com.suiteonix.nix.Organization.services;

import com.suiteonix.nix.spi.location.HomeAddress;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record OrganizationUpdateDto(
        @Schema(example = "Example Business")
        String name,
        @Schema(example = "EB")
        String shortName,
        @Schema(example = "IT")
        String industry,
        String bio,
        Organization.Detail detail,
        HomeAddress.Create address,
        Socials socials,
        Contacts contact
) {

    public OrganizationUpdateDto {
        if (shortName != null) shortName = shortName.toLowerCase();
    }

    @Schema(name = "OrganizationUpdateDto.Details")
    public record Details(
            @Schema(example = "This is a business about something")
            String about,
            @Schema(example = "2020-01-01")
            LocalDate dateEstablished,
            @Schema(example = "1234567890")
            String registrationNumber,
            @Schema(example = "CA")
            String registrationCountry) {
    }

    @Schema(name = "OrganizationUpdateDto.Socials")
    public record Socials(
            @Schema(example = "https://www.example.com")
            String website,
            @Schema(example = "https://www.facebook.com/example")
            String facebook,
            @Schema(example = "https://twitter.com/example")
            String twitter,
            @Schema(example = "https://www.instagram.com/example")
            String instagram,
            @Schema(example = "https://www.linkedin.com/in/example")
            String linkedin,
            @Schema(example = "https://www.youtube.com/channel/example")
            String youtube,
            @Schema(example = "https://www.snapchat.com/add/example")
            String snapchat,
            @Schema(example = "https://www.pinterest.com/example")
            String pinterest
    ) {
    }

    @Schema(name = "OrganizationUpdateDto.Contacts")
    public record Contacts(
            @Schema(example = "business@email.com")
            String email,
            @Schema(example = "+919876543210")
            String phone) {
    }
}
