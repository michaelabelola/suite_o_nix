package com.suiteonix.nix.User.internal;

import com.suiteonix.nix.Organization.services.OrgID;
import com.suiteonix.nix.User.service.UserCreateDto;
import com.suiteonix.nix.User.service.UserID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserModule {
    private final UserRepository userRepository;

    @Transactional
    public UserModel registerUser(UserCreateDto user) {
        UserModel userModel = UserModel.NEW(user);
        return userRepository.save(userModel);
    }

    @Transactional
    public UserModel registerUser(UserCreateDto user, MultipartFile avatar) {
        UserModel userModel = UserModel.NEW(user);
        userModel.getAvatar().upload(avatar, userModel.id.filePath(), "avatar");
        return userRepository.save(userModel);
    }

    public UserModel registerUser(UserCreateDto userCreate, MultipartFile avatar, OrgID orgId, UserID registerer) {
        UserModel userModel = UserModel.NEW(userCreate, orgId, registerer);
        userModel.getAvatar().upload(avatar, userModel.id.filePath(), "avatar");
        return userRepository.save(userModel);
    }
}
