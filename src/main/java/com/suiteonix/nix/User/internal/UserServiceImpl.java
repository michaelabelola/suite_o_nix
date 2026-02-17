package com.suiteonix.db.nix.User.internal;

import com.suiteonix.db.nix.User.service.UserService;
import com.suiteonix.db.nix.shared.ids.NixID;
import com.suiteonix.db.nix.User.service.User;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserModule userModule;

    @Cacheable(value = "users", key = "#id.toString()")
    @Override
    public Optional<User> findById(@NonNull NixID id) {
        return userRepository.findById(id).map(UserMapper.INSTANCE::toUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User registerUser(User.@NonNull Create create) {
        return UserMapper.INSTANCE.toUser(userModule.registerUser(create));
    }
}
