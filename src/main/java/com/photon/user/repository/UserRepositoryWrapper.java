package com.photon.user.repository;

import com.photon.user.entity.User;
import com.photon.user.exceptions.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryWrapper {

    private final UserRepository userRepository;

    public User fetchById(int userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
    }
}
