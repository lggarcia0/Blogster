package com.revature.security;

import com.revature.services.BlogsterUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private BlogsterUserDetailService blogsterUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( customizer -> {
                    customizer
//                     .requestMatchers("/**").hasRole("ADMIN")
//                     .requestMatchers("/users/**").hasAnyRole("USER", "ADMIN")
//                     .requestMatchers("/blog/**").hasRole("USER")
//                     .requestMatchers("/comment/**").hasRole("USER")
                       .requestMatchers("**", "/**", "/blogsterusers/**").permitAll()
                       .anyRequest().authenticated();
                })
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return blogsterUserDetailService;
        /** OR comment above portion and uncomment below portion if needed for testing purposes */
        /* UserDetails userDetails = User.builder()
                .username("user")
                .password("$2a$12$0uTq1F9/gtgY5JSDwxM4UOUyo.rXG17Ni7WLldosQsQ2/x.E9Zg.u")
                .roles("USER")
                .build();
        UserDetails adminDetails = User.builder()
                .username("admin")
                .password("$2a$12$0uTq1F9/gtgY5JSDwxM4UOUyo.rXG17Ni7WLldosQsQ2/x.E9Zg.u")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails, adminDetails); */
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setUserDetailsService(userDetailsService());
        daoAuthProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthProvider;
    }




}
