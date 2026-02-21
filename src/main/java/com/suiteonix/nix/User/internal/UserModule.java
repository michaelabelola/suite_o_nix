package com.suiteonix.nix.User.internal;

import com.suiteonix.nix.Organization.services.OrgID;
import com.suiteonix.nix.User.service.UserCreateDto;
import com.suiteonix.nix.User.service.UserEvents;
import com.suiteonix.nix.shared.ids.NixID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserModule {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public UserModel registerUser(UserCreateDto user) {
        UserModel userModel = UserModel.NEW(user);
        return userRepository.save(userModel);
    }

    @Transactional
    public void registerDefaultOrgUser(OrgID orgId, NixID userId) {
        userRepository
                .findById(userId)
                .map(userModel -> userModel.CLONE(orgId))
                .map(userRepository::save)
                .map(UserMapper.INSTANCE::toUser)
                .ifPresent(user -> applicationEventPublisher.publishEvent(UserEvents.OrgUserCreated.of(userId, orgId)));
    }
}
