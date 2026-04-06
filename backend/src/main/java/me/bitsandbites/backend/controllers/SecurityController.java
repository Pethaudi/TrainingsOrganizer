package me.bitsandbites.backend.controllers;

import me.bitsandbites.backend.repositories.RegisteredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("security")
public class SecurityController {

    private final RegisteredRepository repo;

    @Autowired
    public SecurityController(RegisteredRepository repo) {
        this.repo = repo;
    }

    @GetMapping()
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/login")
    public Boolean login(@RequestParam String username, @RequestParam(required = false) Boolean asTrainer) {
        return this.repo.existsByName(username);
    }
}
