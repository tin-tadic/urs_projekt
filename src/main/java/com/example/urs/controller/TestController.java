package com.example.urs.controller;

import com.example.urs.model.Something;
import com.example.urs.repository.SomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class TestController {
    @Autowired
    SomeRepository someRepository;

    @GetMapping("/test")
    public List<Something> asd() {
        return someRepository.findAll();
    }

}
