package com.suiteonix.nix.User.internal;

import com.suiteonix.nix.User.service.User;
import com.suiteonix.nix.User.service.UserService;
import com.suiteonix.nix.shared.ids.NixID;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
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
    @Transactional
    public User registerUser(User.@NonNull Create create) {
        return UserMapper.INSTANCE.toUser(userModule.registerUser(create));
    }
}
