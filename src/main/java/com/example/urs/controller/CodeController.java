package com.example.urs.controller;

import com.example.urs.dto.AddCodeDTO;
import com.example.urs.dto.ParticipantDTO;
import com.example.urs.model.Code;
import com.example.urs.model.SubmitPresenceDTO;
import com.example.urs.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class CodeController {
    @Autowired
    CodeRepository codeRepository;

    @GetMapping("/")
    public String getAllCodes(Model model, Authentication authentication, AddCodeDTO addCodeDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        List<Code> userCodes = codeRepository.findByOwnerOrderByValidUntilDesc(userDetails.getUsername());

        model.addAttribute("codes", userCodes);
        model.addAttribute("user", userDetails);
        return "index";
    }

    @PostMapping("/generate")
    public String postGenerateNewCode(AddCodeDTO addCodeDTO, Authentication authentication) {
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

        // Extra security to code uniqueness
        generatedCode = generatedCode.concat(String.valueOf(Instant.now().atZone(ZoneOffset.UTC).getYear()));
        generatedCode = generatedCode.concat(String.valueOf(Instant.now().atZone(ZoneOffset.UTC).getDayOfYear()));
        generatedCode = generatedCode.concat(String.valueOf(Instant.now().atZone(ZoneOffset.UTC).getMinute()));


        codeRepository.save(
                new Code(
                        generatedCode,
                        Instant.now().plus(addCodeDTO.getValidMinutes(), ChronoUnit.MINUTES),
                        userDetails.getUsername()
                )
        );

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCode(@PathVariable("id") long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<Code> code = codeRepository.findById(id);

        // Prevents deleting other users' codes
        if (code.isPresent() && Objects.equals(code.get().getOwner(), userDetails.getUsername())) {
            codeRepository.delete(code.get());
        }

        return "redirect:/";
    }

    @GetMapping("/codeDetails/{id}")
    public String codeDetails(@PathVariable("id") long id, Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<Code> code = codeRepository.findById(id);

        // No code or other user's code
        if (code.isEmpty() || !Objects.equals(code.get().getOwner(), userDetails.getUsername())) {
            return "redirect:/";
        }

        List<ParticipantDTO> participantDTOs = new ArrayList<>();
        for (String participant : code.get().getListOfParticipants()) {
            participantDTOs.add(
                    new ParticipantDTO(participant)
            );
        }

        model.addAttribute("code", code.get());
        model.addAttribute("participants", participantDTOs);
        model.addAttribute("user", userDetails);

        return "codeDetails";
    }

    @PostMapping("/submitPresence")
    public ResponseEntity<String> submitPresence(@RequestBody SubmitPresenceDTO submitPresenceDTO) {
        Optional<Code> optionalCode = codeRepository.findByCode(submitPresenceDTO.getCode());

        if (optionalCode.isEmpty()) {
            return new ResponseEntity<>("No code", HttpStatus.OK);
        } else if ((optionalCode.get().getValidUntil().compareTo(Instant.now())) < 0) {
            return new ResponseEntity<>("Code is no longer valid.", HttpStatus.OK);
        }
        Code code = optionalCode.get();

        code.addParticipant(submitPresenceDTO.getName());
        codeRepository.save(code);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
