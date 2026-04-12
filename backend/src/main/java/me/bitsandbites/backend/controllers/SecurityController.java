package me.bitsandbites.backend.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.bitsandbites.backend.dtos.Role;
import me.bitsandbites.backend.dtos.UserDTO;
import me.bitsandbites.backend.repositories.RegisteredRepository;
import org.apache.catalina.User;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.JSONStringer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

class Credentials {
    private String username;
    private String password;
    public Credentials(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString(Integer id, Role role) {
        var json = new JSONStringer();
        json.object();
        json.key("username").value(this.username);
        json.key("password").value(this.password);
        json.key("id").value(id);
        json.key("role").value(role);
        json.endObject();
        return json.toString();
    }
}

@RestController()
@RequestMapping("security")
public class SecurityController {

    private final RegisteredRepository repo;

    @Autowired
    public SecurityController(RegisteredRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody Credentials creds, HttpServletResponse response) {
        var decodedPassword = new String(Base64.getDecoder().decode(creds.getPassword()));
        var user = repo.authenticateUser(creds.getUsername(), decodedPassword);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else {
            var userRole = repo.isTrainer(creds.getUsername()) ? Role.Trainer : Role.User;
            var token = Base64.getEncoder().encodeToString(
                    creds.toString(user.get().getId(), userRole).getBytes()
            );
            var options = "HttpOnly; SameSite=Strict; Path=/; Max-Age=2592000;";
            response.addHeader(
                    "Set-Cookie",
                    "token=" + token + "; " + options
            );
            return new UserDTO(
                    user.get().getId(),
                    creds.getUsername(),
                    userRole
            );
        }
    }

    @GetMapping("/authenticate")
    public UserDTO authenticate(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        var tokenCookie = Arrays.stream(request.getCookies())
                .filter(c -> Objects.equals(c.getName(), "token"))
                .findFirst();

        if (tokenCookie.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } else {
            var tokenValue = new JSONObject(new String(Base64.getDecoder().decode(tokenCookie.get().getValue())));
            var username = tokenValue.getString("username");
            var password = new String(Base64.getDecoder().decode(tokenValue.getString("password").getBytes()));
            if (repo.isUserAndPasswordCorrect(
                    username,
                    password
            )) {
                return new UserDTO(
                        tokenValue.getInt("id"),
                        tokenValue.getString("username"),
                        Role.valueOf(tokenValue.getString("role"))
                );
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
    }
}
