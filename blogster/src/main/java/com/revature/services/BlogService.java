package com.revature.services;

import com.revature.models.Blog;
import com.revature.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    // Add methods here

    //Create a new Blog (a bit empty for now but we will add more to it later)
    public void createBlog(Blog blog) {
        blogRepository.save(blog);
    }
}
