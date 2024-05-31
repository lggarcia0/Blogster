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


@RestController
@RequestMapping("users")
@CrossOrigin(origins = "http://localhost:3000",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH},
        allowCredentials = "true")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
        return new ResponseEntity<User>(userService.loginUser(loginAttempt), HttpStatus.OK);
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
