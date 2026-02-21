package com.suiteonix.nix.Organization.services;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.suiteonix.nix.shared.ids.NixID;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "OrganizationQueryDto")
public record OrganizationQueryDto(
        String query,
        @JsonUnwrapped NixID id, String name, String shortName, String industry,
        Details detail,
        boolean isApproved
) {
    @Schema(name = "OrganizationQueryDto.Contacts")
    public record Contacts(String email, String phone) {
    }

    @Schema(name = "OrganizationQueryDto.Details")
    public record Details(LocalDate dateEstablished, String registrationNumber,
                          String registrationCountry, Boolean verified, Boolean approved,
                          Boolean suspended) {
    }

    @Schema(name = "OrganizationQueryDto.Socials")
    public record Socials(
            String website, String facebook, String twitter, String instagram,
            String linkedin, String youtube, String snapchat, String pinterest) {
    }
}
