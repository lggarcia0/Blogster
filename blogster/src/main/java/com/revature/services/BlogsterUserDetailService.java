package com.revature.services;

import com.revature.models.BlogsterUser;
import com.revature.repositories.BlogsterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class BlogsterUserDetailService implements UserDetailsService {

    @Autowired
    private BlogsterUserRepository blogsterUserRepository;

    @Override
    public UserDetails loadUserByUsername(String req_username) throws UsernameNotFoundException {
        Optional<BlogsterUser> blogsterUserOptional = blogsterUserRepository.findByUsername(req_username);
        if(blogsterUserOptional.isPresent()){
            var blogsterUser = blogsterUserOptional.get();
            return User.builder()
                    .username( blogsterUser.getUsername() )
                    .password( blogsterUser.getPassword() )
                    .roles( getRoles(blogsterUser) )
                    .build();
        } else {throw new UsernameNotFoundException(req_username+" not found");}
    }

    public String[] getRoles(BlogsterUser blogsterUser){

        if(blogsterUser.getRole() == null){
            return new String[] {"USER"};
        } else {
            return Arrays.stream(blogsterUser.getRole().split(","))
                    .map(String::trim).toArray(String[]::new);
        }
    }
}
