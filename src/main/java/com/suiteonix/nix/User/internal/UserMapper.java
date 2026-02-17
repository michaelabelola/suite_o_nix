package com.suiteonix.nix.User.internal;

import com.suiteonix.nix.User.service.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserModel userModel);
}
