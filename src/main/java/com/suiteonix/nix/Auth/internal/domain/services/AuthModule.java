package com.suiteonix.nix.Auth.internal.domain.services;

import com.suiteonix.nix.Auth.internal.domain.AuthProfileModel;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserMapper;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserRepository;
import com.suiteonix.nix.Auth.service.AuthProfile;
import com.suiteonix.nix.Mail.NixMailSender;
import com.suiteonix.nix.Mail.TemplateType;
import com.suiteonix.nix.Organization.services.OrgID;
import com.suiteonix.nix.shared.ConfigFlag;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthModule {
    private final AuthUserRepository repository;
    private final RegisterUseCase registerUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;
    private final ResendVerificationUseCase resendVerificationUseCase;
    private final AuthUserRepository authUserRepository;

    @Transactional
    public AuthProfileModel register(AuthProfile.Register create) {
        return registerUseCase.execute(create);
    }

    @Transactional
    AuthProfileModel update(AuthProfileModel authUser) {
        return repository.save(authUser);
    }

    @Transactional
    void delete(AuthProfileModel authUser) {
        repository.delete(authUser);
    }

    @Cacheable(value = "authUser", key = "#id")
    @Transactional(readOnly = true)
    public AuthProfileModel getAuthUserById(Long id) {
        return repository
                .findById(NixID.of(id))
                .orElseThrow(
                        () ->
                                EX.notFound(
                                        "AUTH_USER_NOT_FOUND",
                                        "Auth user not found"));
    }

    @Transactional
    public void verifyEmailByJwt(String token, String orgID) {
        verifyEmailUseCase.verifyByJwt(token, orgID);
    }

    @Transactional
    public void verifyEmailByOtp(String email, String otp, Long orgID) {
        verifyEmailUseCase.verifyByOtp(email, otp, orgID);
    }

    @Transactional
    public void resendVerification(String email, Long orgID) {
        resendVerificationUseCase.resend(email, orgID);
    }

    public void registerOrgUserProfile(NixID id, OrgID orgID) {
        var authProfile = AuthProfileModel.NEW(NixRole.USER,
                AuthProfileModel.SignInOptions.NEW_DISABLED(),
                AuthProfileModel.ConfigFlags.NEW(
                        AuthProfile.ConfigFlags.builder()
                                .jwtAuthEnabled(ConfigFlag.ACTIVE)
                                .linkedAccountLogin(ConfigFlag.ACTIVE)
                                .enableOwnerLogin(ConfigFlag.ACTIVE)
                                .build()
                ), orgID);

        var mainUser = repository.findById(id).orElseThrow(() -> EX.notFound("AUTH_USER_NOT_FOUND", "Auth user not found"));
        authProfile.enableProxySignIn(id);

        authUserRepository.save(authProfile);
        var dto = AuthUserMapper.INSTANCE.toDto(authProfile);
        sendUserCanNowLoginMail(mainUser, dto);
    }

    private void sendUserCanNowLoginMail(AuthProfileModel model, AuthProfile dto) {
//        TODO: Send a mail to the user telling them them can now access their newly registered Organization Account
        NixMailSender.newInstance()
                .to(model.getEmail().get())
                .templateType(TemplateType.THYMELEAF)
                .templateName("auth/user-can-now-login")
                .variable("email", model.getEmail().get())
                .variable("orgId", dto.orgID())
                .queueMail();
    }
}
