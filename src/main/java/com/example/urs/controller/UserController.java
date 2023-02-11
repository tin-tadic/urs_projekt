package com.example.urs.controller;

import com.example.urs.model.User;
import com.example.urs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);

    @GetMapping("/add")
    public String addNewUser() {
        User newUser = new User(
                "tin3", passwordEncoder.encode("tin"), "ROLE_USER"
        );
        userRepository.save(newUser);

        return "redirect:/";
    }

    @GetMapping("/isValid")
    public String isUserValid() {
        if (passwordEncoder.matches("123", passwordEncoder.encode("tin"))) {
            System.out.println("EQS");
        } else {
            System.out.println("AAAAAAAAAAAAAAA");
        }

        return "redirect:/";
    }
}
