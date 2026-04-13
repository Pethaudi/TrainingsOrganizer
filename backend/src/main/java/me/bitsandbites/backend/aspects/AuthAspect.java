package me.bitsandbites.backend.aspects;

import me.bitsandbites.backend.annotations.RequiresAuth;
import me.bitsandbites.backend.dtos.Role;
import me.bitsandbites.backend.helpers.TokenParser;
import me.bitsandbites.backend.repositories.RegisteredRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Base64;

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

        var tokenValue = TokenParser.parseRawToken(requestAttributes.getRequest());
        var username = tokenValue.getString("username");
        var password = new String(Base64.getDecoder().decode(tokenValue.getString("password").getBytes()));

        if (repo.authenticateUser(username, password).isEmpty()) {
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
