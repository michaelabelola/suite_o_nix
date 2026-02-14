package com.suiteonix.nix.Auth.internal;

import com.suiteonix.nix.Auth.infrastructure.AuthUserRepository;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.spi.Auth.AuthUserRegisterDto;
import com.suiteonix.nix.shared.ValueObjects.Email;
import com.suiteonix.nix.shared.ValueObjects.Password;
import com.suiteonix.nix.shared.ValueObjects.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthUserModel register(AuthUserRegisterDto create) {
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
    public AuthUserModel update(AuthUserModel authUser) {
        return repository.save(authUser);
    }

    @Transactional
    public void delete(AuthUserModel authUser) {
        repository.delete(authUser);
    }

    public AuthUserModel getAuthUserById(String id) {
        return repository
                .findById(NixID.of(id))
                .orElseThrow(
                        () ->
                                EX.notFound(
                                        "AUTH_USER_NOT_FOUND",
                                        "Auth user not found"));
    }
}
