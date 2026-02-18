package com.suiteonix.nix.Auth.internal;

import com.suiteonix.nix.Auth.service.AuthProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthUserMapper {
    AuthUserMapper INSTANCE = Mappers.getMapper(AuthUserMapper.class);

    @Mapping(target = "email", source = "email.value")
    @Mapping(target = "phone", source = "phone.value")
    AuthProfile toDto(AuthProfileModel authProfileModel);

    AuthProfile.SignInOptions toSignInOptionDto(AuthProfileModel.SignInOptions signInOptions);

    AuthProfile.ConfigFlags toConfigFlagDto(AuthProfileModel.ConfigFlags configFlags);
}
