package com.example.urs.controller;

import com.example.urs.model.User;
import com.example.urs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/getAll")
    public List<User> getAllCodes(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("Retrieved user with authorities: " + userDetails.getUsername());
        return userRepository.findAll();
    }

    @GetMapping("/add")
    public void addNewUser() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);
        User newUser = new User(
                "tin", passwordEncoder.encode("tin"), "ROLE_USER"
        );
        userRepository.save(newUser);
    }
}
