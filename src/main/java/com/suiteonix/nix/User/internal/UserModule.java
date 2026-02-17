package com.suiteonix.nix.User.internal;

import com.suiteonix.nix.User.service.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserModule {

    private final UserRepository userRepository;

    @Transactional
    public UserModel registerUser(User.Create user) {
        UserModel userModel = UserModel.NEW(user);
        return userRepository.saveAndFlush(userModel);
    }

}
