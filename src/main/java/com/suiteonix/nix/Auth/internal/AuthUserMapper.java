package com.suiteonix.db.nix.Auth.internal;

import com.suiteonix.db.nix.Auth.service.AuthProfile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthUserMapper {
    AuthUserMapper INSTANCE = Mappers.getMapper(AuthUserMapper.class);

    AuthProfile toDto(AuthUserModel authUserModel);
}
