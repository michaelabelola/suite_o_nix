package com.suiteonix.nix.User.internal;

import com.suiteonix.nix.User.service.UserID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, UserID> {
}