package com.suiteonix.nix.Organization.services;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.suiteonix.nix.Common.audit.AuditSection;
import com.suiteonix.nix.Storage.NixImage;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.spi.location.HomeAddress;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDate;

@Schema(name = "Organization")
public record Organization(
        @JsonUnwrapped OrgID id, String name, String shortName, String industry,
        Detail details,
        boolean isApproved,
        boolean isSuspended,
        @JsonUnwrapped(prefix = "logo_") NixImage logo,
        @JsonUnwrapped(prefix = "logoDark_") NixImage logoDark,
        @JsonUnwrapped(prefix = "coverImage_") NixImage coverImage,
        @JsonUnwrapped(prefix = "coverImageDark_") NixImage coverImageDark
) {
    @Schema(name = "Organization.Detailed")
    public record Detailed(
            @JsonUnwrapped OrgID id, String name, String shortName, String industry,
            @JsonUnwrapped(prefix = "owner_") NixID orgID,
            Detail details, HomeAddress address,
            Logos logos,
            @JsonUnwrapped(prefix = "registeredBy_") NixID registeredBy,
            Socials socials, Contact contact,
            AuditSection audit
    ) {
    }

    public record Contact(String email, String phone) {
    }

    public record Detail(String about, LocalDate dateEstablished, String registrationNumber,
                         String registrationCountry, boolean verified, boolean approved,
                         boolean suspended) implements Serializable {
    }

    public record Logos(
            @JsonUnwrapped(prefix = "logo_") NixImage logo,
            @JsonUnwrapped(prefix = "logoDark_") NixImage logoDark,
            @JsonUnwrapped(prefix = "coverImage_") NixImage coverImage,
            @JsonUnwrapped(prefix = "coverImageDark_") NixImage coverImageDark
    ) {
    }

    public record Socials(
            String website, String facebook, String twitter, String instagram,
            String linkedin, String youtube, String snapchat, String pinterest) {
    }
}
