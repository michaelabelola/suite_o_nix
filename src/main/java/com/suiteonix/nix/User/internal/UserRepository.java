package com.suiteonix.nix.User.internal;

import com.suiteonix.nix.shared.ids.NixID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, NixID> {
}