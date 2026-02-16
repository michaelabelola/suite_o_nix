package com.suiteonix.db.nix.User.internal;

import com.suiteonix.db.nix.shared.ids.NixID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, NixID> {
}