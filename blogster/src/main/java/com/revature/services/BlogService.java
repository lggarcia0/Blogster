package com.revature.services;

import com.revature.exceptions.InvalidBlogException;
import com.revature.exceptions.InvalidUserException;
import com.revature.models.Blog;
import com.revature.models.User;
import com.revature.repositories.BlogRepository;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidSearchControlsException;
import java.util.Optional;

@Service
public class BlogService {
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    // Add methods here

    //Create a new Blog (a bit empty for now but we will add more to it later)
    public Blog createBlog(Blog blog, int userId) {
        Optional<User> authorOpt = userRepository.findById(userId);
        if (authorOpt.isEmpty()) {
            throw new InvalidUserException("User not found");
        }
        User author = authorOpt.get();
        blog.setUser(author);
        if (blog.getName() == null || blog.getName().isEmpty()) {
            throw new InvalidBlogException("Blog name cannot be empty");
        }
        Optional<Blog> blogOpt = blogRepository.findByName(blog.getName());
        if (blogOpt.isPresent()) {
            throw new InvalidBlogException("Blog name already exists");
        }
        if (blog.getDescription() == null) {
            blog.setDescription("");
        }
        return blogRepository.save(blog);
    }
}
