package com.revature.controllers;

import com.revature.models.BlogsterUser;
import com.revature.repositories.BlogsterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("blogsterusers")
public class BlogsterUserController {

    @Autowired
    private BlogsterUserRepository blogsterUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register/user")
    public ResponseEntity<BlogsterUser> registerUser(@RequestBody BlogsterUser newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return ResponseEntity.ok(blogsterUserRepository.save(newUser));
    }

}
