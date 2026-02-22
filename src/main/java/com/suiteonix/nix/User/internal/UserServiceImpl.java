package com.suiteonix.nix.User.internal;

import com.suiteonix.nix.Organization.services.OrgID;
import com.suiteonix.nix.User.service.User;
import com.suiteonix.nix.User.service.UserCreateDto;
import com.suiteonix.nix.User.service.UserID;
import com.suiteonix.nix.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserModule userModule;

    @Cacheable(value = "users", key = "#id.toString()")
    @Override
    public Optional<User> findById(@NonNull UserID id) {
        return userRepository.findById(id).map(UserMapper.INSTANCE::toUser);
    }

    @Transactional
    @Override
    public User.Detailed registerUser(@NonNull UserCreateDto create, MultipartFile avatar) {
        return UserMapper.INSTANCE.detailed(userModule.registerUser(create, avatar));
    }

    @Transactional
    @Override
    public User.Detailed registerUser(@NonNull UserCreateDto create) {
        return UserMapper.INSTANCE.detailed(userModule.registerUser(create));
    }

    @Override
    public User registerDefaultOrgUser(UserCreateDto userCreate, MultipartFile avatar, OrgID orgId, UserID registerer) {
        var user = userModule.registerUser(userCreate, avatar, orgId, registerer);
        return UserMapper.INSTANCE.toUser(user);
    }
}
