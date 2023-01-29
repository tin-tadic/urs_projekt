package com.example.urs.controller;

import com.example.urs.model.Code;
import com.example.urs.repository.CodeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/code")
public class CodeController {
    @Autowired
    CodeRepository codeRepository;

    @GetMapping("getAll")
    public List<Code> getAllCodes(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("Retrieved user with authorities: " + userDetails.getUsername());
        return codeRepository.findAll();
    }

}
