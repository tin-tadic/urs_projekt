package com.example.urs.controller;

import com.example.urs.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CodeController {
    @Autowired
    CodeRepository codeRepository;

    @GetMapping("/")
    public String getAllCodes(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("codes", codeRepository.findByOwner(userDetails.getUsername()));
        return "index";
    }

}
