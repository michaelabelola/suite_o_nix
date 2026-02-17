package com.suiteonix.nix.spi.location;


import io.swagger.v3.oas.annotations.media.Schema;

public record HomeAddress(
        String apt_number,
        String street,
        String city,
        String state,
        String country,
        String zipcode,
        Double latitude,
        Double longitude
) {

    public record Create(
            @Schema(description = "RM-123")
            String apt_number,
            @Schema(description = "123 main street")
            String street,
            @Schema(description = "IKJ")
            String city,
            @Schema(description = "LA")
            String state,
            @Schema(description = "NG")
            String country,
            @Schema(description = "12345")
            String zipcode,
            @Schema(description = "6.6137")
            Double latitude,
            @Schema(description = "3.3553")
            Double longitude) {
    }
}
