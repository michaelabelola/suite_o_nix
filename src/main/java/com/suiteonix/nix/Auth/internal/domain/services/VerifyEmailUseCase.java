package com.suiteonix.nix.Auth.internal.domain.services;

import com.suiteonix.nix.Auth.internal.domain.AuthProfileModel;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserRepository;
import com.suiteonix.nix.Auth.service.AuthTokenType;
import com.suiteonix.nix.kernel.security.AuthJwtUtil;
import com.suiteonix.nix.shared.ddd.UseCase;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.ids.NixID;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
class VerifyEmailUseCase {

    private static final Log log = LogFactory.getLog(VerifyEmailUseCase.class);
    private final AuthUserRepository repository;
    private final AuthJwtUtil authJwtUtil;
    private final AuthUserLookupHelper lookupHelper;

    /**
     * Verify email via JWT link: GET /auth/verify-email?token=&orgID=
     */
    @Transactional
    public void verifyByJwt(String token, String orgID) {
        Claims claims = authJwtUtil.getClaimsFromToken(token);
        if (claims == null)
            throw EX.badRequest("INVALID_VERIFICATION_TOKEN", "The verification link is invalid or has expired");

        NixID userId = NixID.of(claims.getSubject());
        AuthProfileModel authUser = repository.findById(userId)
                .orElseThrow(() -> EX.notFound("AUTH_USER_NOT_FOUND", "Account not found"));

        // If a concrete orgID was supplied, verify it matches the stored owner
        NixID ownerOrgID = AuthUserLookupHelper.toNixId(orgID);
        log.error(!AuthUserLookupHelper.isSystemOrAnonymous(ownerOrgID));
        log.error(!authUser.getOrgID().equals(ownerOrgID));
        if (!AuthUserLookupHelper.isSystemOrAnonymous(ownerOrgID)
                && !authUser.getOrgID().equals(ownerOrgID)) {
            throw EX.badRequest("INVALID_VERIFICATION_TOKEN", "The verification link is invalid");
        }

        if (authUser.isEmailVerified())
            throw EX.conflict("EMAIL_ALREADY_VERIFIED", "This email address has already been verified");

        boolean hasValidToken = authUser.getTokens().stream()
                .anyMatch(t -> t.type() == AuthTokenType.EMAIL_VERIFICATION_JWT
                        && token.equals(t.value())
                        && t.isNotExpired());
        if (!hasValidToken)
            throw EX.badRequest("INVALID_VERIFICATION_TOKEN", "The verification link is invalid or has expired");

        clearVerificationTokens(authUser);
        authUser.setEmailVerified(true);
        repository.save(authUser);
    }

    /**
     * Verify email via OTP code: POST /auth/verify-email  body: {email, token, orgID?}
     */
    @Transactional
    public void verifyByOtp(String email, String otp, String orgID) {
        AuthProfileModel authUser = lookupHelper.findByEmailScoped(email, orgID);

        if (authUser.isEmailVerified())
            throw EX.conflict("EMAIL_ALREADY_VERIFIED", "This email address has already been verified");

        boolean hasValidOtp = authUser.getTokens().stream()
                .anyMatch(t -> t.type() == AuthTokenType.EMAIL_VERIFICATION_OTP
                        && otp.equals(t.value())
                        && t.isNotExpired());
        if (!hasValidOtp)
            throw EX.badRequest("INVALID_VERIFICATION_TOKEN", "The OTP code is invalid or has expired");

        clearVerificationTokens(authUser);
        authUser.setEmailVerified(true);
        repository.save(authUser);
    }

    private void clearVerificationTokens(AuthProfileModel authUser) {
        authUser.getTokens().removeIf(t ->
                t.type() == AuthTokenType.EMAIL_VERIFICATION_OTP
                        || t.type() == AuthTokenType.EMAIL_VERIFICATION_JWT);
    }
}
