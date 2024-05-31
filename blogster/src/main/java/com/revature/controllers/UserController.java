package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // User can update password and/or email
    @PostMapping("/{userId}/update")
    public ResponseEntity<?> updateUser(@RequestBody User modifiedUser, @PathVariable int userId) {
        try {
            userService.updateUser(modifiedUser, userId);
            return ResponseEntity.status(200).body("User Successfully Updated");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
