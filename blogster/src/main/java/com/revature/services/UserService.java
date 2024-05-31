package com.revature.services;

import com.revature.exceptions.AccountCreationException;
import com.revature.exceptions.InvalidLoginException;
import com.revature.exceptions.UserAlreadyExistsException;
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

    
    public User registerUser(User newUser) {
        if (newUser.getPassword().length() < 8) {
            throw new AccountCreationException("Use a longer password!");
        }

        Optional<User> optionalUser = userRepository.findByUsername(newUser.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException(newUser.getUsername() + " already exists!");
        }

        Optional<User> optionalEmail = userRepository.findByEmail(newUser.getEmail());
        if (optionalEmail.isPresent()) {
            throw new UserAlreadyExistsException(newUser.getEmail() + " already has an account!");
        }
        return userRepository.save(newUser);
    }

    public User loginUser(User loginAttempt) {
        User user = userRepository.findByUsername(loginAttempt.getUsername())
                .orElseThrow(() -> new InvalidLoginException("Invalid username or password"));
        if (user.getPassword().equals(loginAttempt.getPassword()))
            return user;
        else throw new InvalidLoginException("Invalid username or password");
    }
    
    public void updateUser(User modifiedUser, int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();
        String password = modifiedUser.getPassword();
        //Needs to throw an exception for when the password is not allowed
        if (password != null && password.length() >= 8) {
            user.setPassword(password);
        }
        //Needs to add a check if the email is already registered and throw an exception if not allowed
        String email = modifiedUser.getEmail();
        if (email != null && email.contains("@")) {
            user.setEmail(email);
        }
        userRepository.save(user);
    }
}
