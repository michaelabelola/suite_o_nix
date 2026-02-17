package com.suiteonix.nix.User.service;


import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.ids.NixID;

import java.util.Optional;
import java.util.function.Supplier;

public interface UserService {
    Optional<User> findById(NixID id);

    default <X extends Throwable> User findByIdElseThrow(NixID id, Supplier<X> exceptionSupplier) throws X{
        return findById(id).orElseThrow(exceptionSupplier);
    }

    default User findByIdElseThrow(NixID id) {
        return findByIdElseThrow(id, () -> EX.notFound("USER_NOT_FOUND", "User not found"));
    }

    User registerUser(User.Create user);

}
