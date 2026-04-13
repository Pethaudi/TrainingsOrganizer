package me.bitsandbites.backend.helpers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import me.bitsandbites.backend.dtos.Role;
import me.bitsandbites.backend.dtos.UserDTO;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

public class TokenParser {
    public static JSONObject parseRawToken(HttpServletRequest request) {
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

        return new JSONObject(new String(Base64.getDecoder().decode(tokenCookie.get().getValue())));
    }

    public static UserDTO parseFromRequest(HttpServletRequest request) {
        var tokenValue = parseRawToken(request);
        return new UserDTO(
                tokenValue.getInt("id"),
                tokenValue.getString("username"),
                tokenValue.getEnum(Role.class, "role")
        );
    }
}
