package com.example.urs.controller;

import com.example.urs.model.User;
import com.example.urs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/add")
    public String addNewUser() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);
        User newUser = new User(
                "tin3", passwordEncoder.encode("tin"), "ROLE_USER"
        );
        userRepository.save(newUser);

        return "redirect:/";
    }
}
