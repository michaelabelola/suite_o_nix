package com.suiteonix.nix.Auth.internal.domain;

import com.suiteonix.nix.Auth.internal.domain.services.AuthModule;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserMapper;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserRepository;
import com.suiteonix.nix.Auth.service.AuthProfile;
import com.suiteonix.nix.Auth.service.AuthenticationService;
import com.suiteonix.nix.Organization.services.OrgID;
import com.suiteonix.nix.shared.ConfigFlag;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthModule authModule;
    private final AuthUserRepository authUserRepository;

    @Override
    @Transactional
    public @NonNull AuthProfile register(AuthProfile.@NonNull Register registerDto) {
        var user = authModule.register(registerDto);

        return AuthUserMapper.INSTANCE.toDto(user);
    }

    public AuthProfile registerOrgUserProfile(NixID id, OrgID orgID) {
        var authProfile = AuthProfileModel.NEW(NixRole.USER,
                AuthProfileModel.SignInOptions.NEW_DISABLED(),
                AuthProfileModel.ConfigFlags.NEW(
                        AuthProfile.ConfigFlags.builder()
                                .jwtAuthEnabled(ConfigFlag.ACTIVE)
                                .linkedAccountLogin(ConfigFlag.ACTIVE)
                                .enableOwnerLogin(ConfigFlag.ACTIVE)
                                .build()
                ), orgID);

        var mainUser = authUserRepository.findById(id).orElseThrow(() -> EX.notFound("AUTH_USER_NOT_FOUND", "Auth user not found"));
        authProfile.enableProxySignIn(id);

        authUserRepository.save(authProfile);
        return AuthUserMapper.INSTANCE.toDto(authProfile);
    }

}
