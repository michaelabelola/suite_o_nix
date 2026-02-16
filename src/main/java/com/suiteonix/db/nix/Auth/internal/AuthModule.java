package com.suiteonix.db.nix.Auth.internal;

import com.suiteonix.db.nix.shared.exceptions.EX;
import com.suiteonix.db.nix.shared.ids.NixID;
import com.suiteonix.db.nix.Auth.service.AuthProfile;
import com.suiteonix.db.nix.shared.ValueObjects.Email;
import com.suiteonix.db.nix.shared.ValueObjects.Password;
import com.suiteonix.db.nix.shared.ValueObjects.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class AuthModule {
    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Cacheable(value = "authUser")
    @Transactional
    public AuthUserModel register(AuthProfile.Register create) {
        repository.findById(create.id()).ifPresent(authUser -> {
            throw EX.conflict("AUTH_USER_ALREADY_EXISTS", "Auth user already exists");
        });
        AuthUserModel authUser = AuthUserModel.NEW(
                create.id(),
                create.role(),
                Email.NEW(create.email()),
                Phone.NEW(create.phone()),
                Password.NewEncodedPassword(create.password(), passwordEncoder)
        );
        return repository.save(authUser);
    }

    @Transactional
    AuthUserModel update(AuthUserModel authUser) {
        return repository.save(authUser);
    }

    @Transactional
    void delete(AuthUserModel authUser) {
        repository.delete(authUser);
    }

    AuthUserModel getAuthUserById(String id) {
        return repository
                .findById(NixID.of(id))
                .orElseThrow(
                        () ->
                                EX.notFound(
                                        "AUTH_USER_NOT_FOUND",
                                        "Auth user not found"));
    }
}
