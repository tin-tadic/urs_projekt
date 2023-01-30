package com.example.urs.controller;

import com.example.urs.dto.CodeDTO;
import com.example.urs.model.Code;
import com.example.urs.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Controller
public class CodeController {
    @Autowired
    CodeRepository codeRepository;

    @GetMapping("/")
    public String getAllCodes(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        List<Code> userCodes = codeRepository.findByOwnerOrderByValidUntilDesc(userDetails.getUsername());


        model.addAttribute("codes", userCodes);
        return "index";
    }

    @GetMapping("/generateCode")
    public String getGenerateNewCode(CodeDTO codeDTO) {
        return "generateCode";
    }

    @PostMapping("/generate")
    public String postGenerateNewCode(CodeDTO codeDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        int leftLimit = 48; // letter '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();

        String generatedCode = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString().toUpperCase();

        codeRepository.save(
                new Code(
                        generatedCode,
                        Instant.now().plus(codeDTO.getValidMinutes(), ChronoUnit.MINUTES),
                        userDetails.getUsername()
                )
        );

        return "redirect:/";
    }
}
