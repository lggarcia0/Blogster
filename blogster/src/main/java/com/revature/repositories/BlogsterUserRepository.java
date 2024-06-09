package com.revature.repositories;

import com.revature.models.BlogsterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogsterUserRepository  extends JpaRepository<BlogsterUser, Long> {
    Optional<BlogsterUser> findByUsername(String username);
    Optional<BlogsterUser> findByEmail(String email);
}
