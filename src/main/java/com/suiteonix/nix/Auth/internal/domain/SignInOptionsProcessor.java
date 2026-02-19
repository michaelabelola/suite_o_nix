package com.suiteonix.nix.Auth.internal.domain;

import com.suiteonix.nix.Auth.service.ConfigFlag;
import com.suiteonix.nix.shared.exceptions.EX;

public class SignInOptionsProcessor {

    public void process(AuthProfileModel authUser) {
        if (authUser.getSignInOptions() == null) return;
        verifyEmailAndPasswordAuthentication(authUser);
        verifyPhonePasswordAuthentication(authUser);
    }

    private void verifyEmailAndPasswordAuthentication(AuthProfileModel authUser) {
        var signInOptions = authUser.getSignInOptions();
        if (signInOptions.getEmailAndPassword() == ConfigFlag.ACTIVE)
            if (authUser.getEmail() == null || authUser.getEmail().isEmpty())
                throw EX.badRequest("EMAIL_REQUIRED", "Email is required to enable email and password authentication");
            else verifyPassword(authUser);
    }

    private void verifyPhonePasswordAuthentication(AuthProfileModel authUser) {
        var signInOptions = authUser.getSignInOptions();
        if (signInOptions.getPhoneAndPassword() == ConfigFlag.ACTIVE)
            if (authUser.getPhone() == null || authUser.getPhone().isEmpty())
                throw EX.badRequest("PHONE_REQUIRED", "Phone is required to enable phone and password authentication");
            else verifyPassword(authUser);
    }

    private void verifyPassword(AuthProfileModel authUser) {
        if (authUser.getConfigFlags().getGenerateRandomPassword() != ConfigFlag.ACTIVE
                && authUser.getPassword() == null || authUser.getPassword().isEmpty())
            throw EX.badRequest("PASSWORD_REQUIRED", "Password is required");
    }

}
