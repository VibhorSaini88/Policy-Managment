package com.epam.system.policy_management_core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        log.debug("loadUserByUsername: "+username);
        if ("admin".equals(username)) {
            log.debug("username verified: "+username);
            return User.builder()
                    .username(username)
                    .password(new BCryptPasswordEncoder().encode("Admin@123"))
                    .roles("ADMIN")
                    .build();
        }
        if ("user".equals(username)) {
            log.debug("username verified: "+username);
            return User.builder()
                    .username(username)
                    .password(new BCryptPasswordEncoder().encode("User@123"))
                    .roles("USER")
                    .build();
        } else {
            log.error("username verified error: "+username);
            throw new UsernameNotFoundException("User not found");
        }
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if ("admin".equals(username)) {
//            // password is 'password' encoded once and stored
//            String encodedPassword = "$2a$10$7EQ0YJfZaV7FQ0ZChsuw3OdGV.VL7BzMY5ODTRZMEpZ/Sr4BfKk1a";
//            return new User("admin", encodedPassword, new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User not found");
//        }
//    }

}
