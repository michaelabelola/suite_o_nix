package com.suiteonix.nix.Auth.internal.domain.services;

import com.suiteonix.nix.Auth.internal.domain.AuthProfileModel;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserRepository;
import com.suiteonix.nix.Auth.service.Login;
import com.suiteonix.nix.Common.ddd.UseCase;
import com.suiteonix.nix.Organization.services.OrgID;
import com.suiteonix.nix.shared.ConfigFlag;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import com.suiteonix.nix.shared.principal.Actor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@UseCase
@RequiredArgsConstructor
class LoginUseCase {

    private final AuthUserLookupHelper lookupHelper;
    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthModuleJwtUtil authModuleJwtUtil;

    public Login.Response executeProxyLogin(NixID userId, OrgID orgID) {
        var proxiedUser = repository.findById(userId).orElseThrow(() -> EX.notFound("USER_NOT_FOUND", "User not found"));
        if (proxiedUser.getSignInOptions().getProxySignIn() != ConfigFlag.ACTIVE)
            throw EX.forbidden("PROXY_SIGN_IN_DISABLED", "Proxy sign-in is not enabled for this account");
        if (!Actor.ID().equals(proxiedUser.getSignInOptions().getProxyUserID()))
            throw EX.forbidden("PROXY_SIGN_FORBIDDEN", "Your account is not allowed to sign in via proxy");

        NixID principalId = proxiedUser.getOrgID();
        NixRole principalRole = proxiedUser.getOrgID().role();

        NixID actorId = proxiedUser.getId();
        NixRole actorRole = proxiedUser.getRole();

        String accessToken = authModuleJwtUtil.generateAccessToken(principalId, principalRole, actorId, actorRole);
        String refreshToken = authModuleJwtUtil.generateRefreshToken(principalId, principalRole, actorId, actorRole);
        return new Login.Response(accessToken, refreshToken, "Bearer");
    }

    @Transactional(readOnly = true)
    public Login.Response execute(Login.Request request) {
        AuthProfileModel user = resolveUser(request);

        verifySignInOptions(user, request);
        verifyPassword(user, request.password());
        verifyEmailVerified(request, user);

        String accessToken = authModuleJwtUtil.generateAccessToken(user.getId(), user.getRole(), user.getId(), user.getRole());
        String refreshToken = authModuleJwtUtil.generateRefreshToken(user.getId(), user.getRole(), user.getId(), user.getRole());
        return new Login.Response(accessToken, refreshToken, "Bearer");
    }

//    @Transactional(readOnly = true)
//    public Login.Response execute(Login.Request request) {
//        AuthProfileModel user = resolveUser(request);
//
//        verifySignInOptions(user, request);
//        verifyPassword(user, request.password());
//        verifyEmailVerified(request, user);
//
//        NixID principalId = user.getId();
//        NixRole principalRole = user.getRole();
//
//        NixID actorId;
//        NixRole actorRole;
//
//        var signInOptions = user.getSignInOptions();
//        if (signInOptions != null && signInOptions.getProxySignIn() == ConfigFlag.ACTIVE
//                && signInOptions.getProxyUserID() != null) {
//            AuthProfileModel proxyUser = repository.findById(signInOptions.getProxyUserID())
//                    .orElseThrow(() -> EX.notFound("PROXY_USER_NOT_FOUND", "Proxy user not found"));
//            actorId = proxyUser.getId();
//            actorRole = proxyUser.getRole();
//        } else {
//            actorId = principalId;
//            actorRole = principalRole;
//        }
//
//        String accessToken = authModuleJwtUtil.generateAccessToken(principalId, principalRole, actorId, actorRole);
//        String refreshToken = authModuleJwtUtil.generateRefreshToken(principalId, principalRole, actorId, actorRole);
//        return new Login.Response(accessToken, refreshToken, "Bearer");
//    }

    private AuthProfileModel resolveUser(Login.Request request) {
        if (StringUtils.hasText(request.email())) {
            return lookupHelper.findByEmailScoped(request.email(), request.orgID());
        }
        if (StringUtils.hasText(request.phone())) {
            return lookupHelper.findByPhoneScoped(request.phone(), request.orgID());
        }
        throw EX.badRequest("LOGIN_IDENTIFIER_REQUIRED", "Either email or phone must be provided");
    }

    private void verifySignInOptions(AuthProfileModel user, Login.Request request) {
        var signInOptions = user.getSignInOptions();
        if (signInOptions == null) {
            throw EX.forbidden("SIGN_IN_DISABLED", "Sign-in is not configured for this account");
        }

        if (StringUtils.hasText(request.email()) && StringUtils.hasText(request.password()) && signInOptions.getEmailAndPassword() != ConfigFlag.ACTIVE) {
            throw EX.forbidden("EMAIL_PASSWORD_SIGN_IN_DISABLED", "Email and password sign-in is not enabled for this account");
        }
        if (StringUtils.hasText(request.phone()) && StringUtils.hasText(request.password()) && signInOptions.getPhoneAndPassword() != ConfigFlag.ACTIVE) {
            throw EX.forbidden("PHONE_PASSWORD_SIGN_IN_DISABLED", "Phone and password sign-in is not enabled for this account");
        }
    }

    private void verifyPassword(AuthProfileModel user, String rawPassword) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw EX.unauthorized("INVALID_CREDENTIALS", "Password not available or not provided.");
        }
        if (!passwordEncoder.matches(rawPassword, user.getPassword().value())) {
            throw EX.unauthorized("INVALID_CREDENTIALS", "Password does not match.");
        }
    }

    private void verifyEmailVerified(Login.Request request, AuthProfileModel user) {
        if (request.email() != null && !user.isEmailVerified()) {
            throw EX.forbidden("EMAIL_NOT_VERIFIED", "Email address has not been verified. Please check your inbox.");
        }
    }
}
