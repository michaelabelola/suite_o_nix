package com.suiteonix.nix.User.internal;

import com.suiteonix.nix.Location.LocationMapper;
import com.suiteonix.nix.User.service.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {LocationMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserModel userModel);

    @Mapping(target = "address", source = "address", qualifiedByName = "toDTO")
    User.Detailed detailed(UserModel userModel);
}
