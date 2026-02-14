package com.suiteonix.nix.Auth.internal;

import com.suiteonix.nix.Auth.AuthUserRegisterDto;
import com.suiteonix.nix.shared.ValueObjects.Email;
import com.suiteonix.nix.shared.ValueObjects.Password;
import com.suiteonix.nix.shared.ValueObjects.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AuthUserService {
    private final AuthUserRepository repository;

    public AuthUserModel register(AuthUserRegisterDto create) {
        AuthUserModel authUser = AuthUserModel.NEW(
                create.id(),
                create.role(),
                Email.NEW(create.email()),
                Phone.NEW(create.phone()),
                Password.NewEncodedPassword(create.password(), null)
        );
        return repository.save(authUser);
    }

    public AuthUserModel update(AuthUserModel authUser) {
        return repository.save(authUser);
    }

    public void delete(AuthUserModel authUser) {
        repository.delete(authUser);
    }

}
