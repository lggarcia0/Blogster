package com.revature.controllers;

import com.revature.services.BlogPostService;
import com.revature.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
