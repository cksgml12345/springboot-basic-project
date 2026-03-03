package com.chani.springbootbasicproject.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "project", "University Portfolio",
                "message", "Spring Boot portfolio API is running"
        );
    }
}
