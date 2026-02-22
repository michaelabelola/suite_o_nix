package com.suiteonix.nix.User.service;


import com.suiteonix.nix.Organization.services.OrgID;
import com.suiteonix.nix.shared.exceptions.EX;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Supplier;

public interface UserService {
    Optional<User> findById(UserID id);

    default <X extends Throwable> User findByIdElseThrow(UserID id, Supplier<X> exceptionSupplier) throws X {
        return findById(id).orElseThrow(exceptionSupplier);
    }

    default User findByIdElseThrow(UserID id) {
        return findByIdElseThrow(id, () -> EX.notFound("USER_NOT_FOUND", "User not found"));
    }

    User.Detailed registerUser(UserCreateDto user, MultipartFile avatar);

    User.Detailed registerUser(UserCreateDto user);

    @Transactional
    User registerDefaultOrgUser(UserCreateDto user, MultipartFile avatar, OrgID orgId, UserID userId);
}
