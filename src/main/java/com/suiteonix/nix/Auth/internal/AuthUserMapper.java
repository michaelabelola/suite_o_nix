package com.suiteonix.nix.Auth.internal;

import com.suiteonix.nix.Auth.AuthUser;
import org.mapstruct.Mapper;

@Mapper
public interface AuthUserMapper {
    AuthUser toModel(AuthUserModel authUserModel);
}
