package com.project.user_service.service;


import com.project.user_service.client.NotificationClient;
import com.project.user_service.model.User;
import com.project.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final NotificationClient notificationClient;


    public User createUser(User user) {

        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        notificationClient.sendNotification(
                savedUser.getId().toString(),
                "User account successfully created"
        );

        return savedUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}