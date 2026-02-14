package com.suiteonix.nix.Auth.internal;

import com.suiteonix.nix.spi.Auth.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthUserMapper {
    AuthUserMapper INSTANCE = Mappers.getMapper(AuthUserMapper.class);

    AuthUser toDto(AuthUserModel authUserModel);
}
