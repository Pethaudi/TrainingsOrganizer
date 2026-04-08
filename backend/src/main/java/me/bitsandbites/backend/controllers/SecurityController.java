package me.bitsandbites.backend.controllers;

import me.bitsandbites.backend.dtos.Role;
import me.bitsandbites.backend.dtos.UserDTO;
import me.bitsandbites.backend.repositories.RegisteredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController()
@RequestMapping("security")
public class SecurityController {

    private final RegisteredRepository repo;

    @Autowired
    public SecurityController(RegisteredRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/login")
    public UserDTO login(@RequestParam String username) {
        var foundRegistered = this.repo.findByName(username);
        if (foundRegistered == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        UserDTO user = new UserDTO(this.repo.findByName(username));
        if (repo.isTrainer(username)) {
            user.setRole(Role.Trainer);
        } else {
            user.setRole(Role.User);
        }
        return user;
    }

    @GetMapping("/trainer-login")
    public Boolean loginTrainer(@RequestParam String username) {
        return repo.isTrainer(username);
    }
}
