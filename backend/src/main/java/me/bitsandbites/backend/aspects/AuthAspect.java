package me.bitsandbites.backend.aspects;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import me.bitsandbites.backend.annotations.RequiresAuth;
import me.bitsandbites.backend.dtos.Role;
import me.bitsandbites.backend.repositories.RegisteredRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

@Aspect
@Component
public class AuthAspect {

    private final RegisteredRepository repo;

    @Autowired
    public AuthAspect(RegisteredRepository repo) {
        this.repo = repo;
    }

    @Before("@annotation(requiresAuth)")
    public void checkAuth(RequiresAuth requiresAuth) {
        var requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        HttpServletRequest request = requestAttributes.getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        var tokenCookie = Arrays.stream(cookies)
                .filter(c -> Objects.equals(c.getName(), "token"))
                .findFirst();

        if (tokenCookie.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        var tokenValue = new JSONObject(new String(Base64.getDecoder().decode(tokenCookie.get().getValue())));
        var username = tokenValue.getString("username");
        var password = new String(Base64.getDecoder().decode(tokenValue.getString("password").getBytes()));

        if (!repo.isUserAndPasswordCorrect(username, password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Role[] requiredRoles = requiresAuth.role();
        if (requiredRoles.length > 0) {
            var userRole = Role.valueOf(tokenValue.getString("role"));
            var hasRole = Arrays.stream(requiredRoles).anyMatch(r -> r == userRole);
            if (!hasRole) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }
    }
}