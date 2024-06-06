package com.revature.controllers;

import com.revature.exceptions.InvalidBlogException;
import com.revature.exceptions.InvalidUserException;
import com.revature.models.Blog;
import com.revature.services.BlogPostService;
import com.revature.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("blog")
@CrossOrigin(origins = "http://localhost:3000",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH},
        allowCredentials = "true")
public class BlogController {
    private final BlogService blogService;
    private final BlogPostService blogPostService;

    @Autowired
    public BlogController(BlogService blogService, BlogPostService blogPostService) {
            this.blogService = blogService;
            this.blogPostService = blogPostService;
    }

    // Add endpoints here
    @PostMapping("{userId}")
    public ResponseEntity<?> createBlog(@RequestBody Blog incomingBlog, @RequestParam int userId) {
        try {
            Blog createdBlog = blogService.createBlog(incomingBlog, userId);
            return ResponseEntity.status(201).body(createdBlog);
        } catch (InvalidBlogException | InvalidUserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Add exception handlers here
    @ExceptionHandler(InvalidBlogException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleInvalidBlogException(InvalidBlogException e) {
        return e.getMessage();
    }
    @ExceptionHandler(InvalidUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleInvalidUserException(InvalidUserException e) {
        return e.getMessage();
    }
}
