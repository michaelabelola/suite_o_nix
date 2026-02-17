package com.suiteonix.nix.spi.location;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeAddressModel {
    @Schema(description = "RM-123")
    String address_apt_number;
    @Schema(description = "123 main street")
    String address_street;
    @Schema(description = "IKJ")
    String address_city;
    @Schema(description = "LA")
    String address_state;
    @Schema(description = "NG")
    String address_country;
    @Schema(description = "12345")
    String address_zipcode;
    @Schema(description = "6.6137")
    Double address_latitude;
    @Schema(description = "3.3553")
    Double address_longitude;


    public static HomeAddressModel NEW(HomeAddress.Create create) {
        if (create == null) return null;
        return HomeAddressModel.builder()
                .address_apt_number(create.apt_number())
                .address_street(create.street())
                .address_city(create.city())
                .address_state(create.state())
                .address_country(create.country())
                .address_zipcode(create.zipcode())
                .address_latitude(create.latitude())
                .address_longitude(create.longitude())
                .build();
    }
}
