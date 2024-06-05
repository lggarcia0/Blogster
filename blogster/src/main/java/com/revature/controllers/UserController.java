package com.revature.controllers;

import com.revature.exceptions.InvalidLoginException;
import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.revature.exceptions.AccountCreationException;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("users")
@CrossOrigin(origins = "http://localhost:3000",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH}, allowCredentials = "true")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // User can update password and/or email
//    @PostMapping("/{userId}/update")
//    public ResponseEntity<?> updateUser(@RequestBody User modifiedUser, @PathVariable int userId) {
//        try {
//            userService.updateUser(modifiedUser, userId);
//            return ResponseEntity.status(200).body("User Successfully Updated");
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
  
    @PostMapping
    public ResponseEntity<User> registerNewUserHandler(@RequestBody User newUser) {
        if (newUser == null || newUser.getEmail() == null || newUser.getEmail().isEmpty()
            || newUser.getUsername() == null || newUser.getUsername().isEmpty()) {
            throw new AccountCreationException("Please fill out all fields");
        }
        User registerUser = userService.registerUser(newUser);

        return new ResponseEntity<User>(registerUser, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<User> loginHandler(@RequestBody User loginAttempt) {
        return new ResponseEntity<User>(userService.loginUser(loginAttempt), OK);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody String handleUserAlreadyExists(UserAlreadyExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(AccountCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleAccountCreationException(AccountCreationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(InvalidLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody String handleInvalidLogin(InvalidLoginException e) {
        return e.getMessage();
    }
}
