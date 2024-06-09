package com.revature.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Table(name = "blogster_users")
public class BlogsterUser {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String password;

    private String role;

    // A user can make many comments
//    @OneToMany(mappedBy = "blogster_user")
//    private Set<Comment> comments;
}