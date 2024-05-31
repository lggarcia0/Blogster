package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateUser(User modifiedUser, int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();
        String password = modifiedUser.getPassword();
        if (password != null && password.length() >= 8) {
            user.setPassword(password);
        }
        String email = modifiedUser.getEmail();
        if (email != null && email.contains("@")) {
            user.setEmail(email);
        }
        userRepository.save(user);
    }
}
