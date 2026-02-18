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
    @Schema(name = "HomeAddress.Create", description = "HomeAddress.Create")
    public record Create(
            @Schema(example = "RM-123")
            String apt_number,
            @Schema(example = "123 main street")
            String street,
            @Schema(example = "IKJ")
            String city,
            @Schema(example = "LA")
            String state,
            @Schema(example = "NG")
            String country,
            @Schema(example = "12345")
            String zipcode,
            @Schema(example = "6.6137")
            Double latitude,
            @Schema(example = "3.3553")
            Double longitude) {
    }
}
