package com.suiteonix.nix.Location;

import com.suiteonix.nix.spi.location.HomeAddress;
import com.suiteonix.nix.spi.location.HomeAddressModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    @Named("toDTO")
    @Mapping(target = "apt_number", source = "address_apt_number")
    @Mapping(target = "street", source = "address_street")
    @Mapping(target = "city", source = "address_city")
    @Mapping(target = "state", source = "address_state")
    @Mapping(target = "country", source = "address_country")
    @Mapping(target = "zipcode", source = "address_zipcode")
    @Mapping(target = "latitude", source = "address_latitude")
    @Mapping(target = "longitude", source = "address_longitude")
    HomeAddress toDTO(HomeAddressModel homeAddressModel);
}
