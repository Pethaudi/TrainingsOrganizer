package me.bitsandbites.backend.aspects;

import me.bitsandbites.backend.annotations.RequiredAuthOrganisationId;
import me.bitsandbites.backend.annotations.RequiresAuth;
import me.bitsandbites.backend.dtos.Role;
import me.bitsandbites.backend.helpers.RoleValidator;
import me.bitsandbites.backend.helpers.TokenParser;
import me.bitsandbites.backend.repositories.MembersOfOrganisationRepository;
import me.bitsandbites.backend.repositories.RegisteredRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.jspecify.annotations.NonNull;
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
    private final MembersOfOrganisationRepository membersRepo;

    @Autowired
    public AuthAspect(RegisteredRepository repo, MembersOfOrganisationRepository membersRepo) {
        this.repo = repo;
        this.membersRepo = membersRepo;
    }

    @Before("@annotation(requiresAuth)")
    public void checkAuth(JoinPoint joinPoint, RequiresAuth requiresAuth) {
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
        if (requiredRoles.length == 0) {
            return;
        }

        Integer organisationId = getOrganisationId(joinPoint);

        var userId = tokenValue.getInt("id");
        var membership = membersRepo.findByRegisteredIdAndOrganisationId(userId, organisationId);
        if (membership.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        var isRoleMatching = Arrays.stream(requiredRoles).anyMatch(r -> RoleValidator.isRoleHigherOrEqual(membership.get().getRole(), r));
        if (!isRoleMatching) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    private static @NonNull Integer getOrganisationId(JoinPoint joinPoint) {
        var proxyMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        var targetMethod = AopUtils.getMostSpecificMethod(proxyMethod, joinPoint.getTarget().getClass());
        var params = targetMethod.getParameters();
        var args = joinPoint.getArgs();

        Integer organisationId = null;
        for (int i = 0; i < params.length; i++) {
            if (params[i].isAnnotationPresent(RequiredAuthOrganisationId.class)) {
                organisationId = (Integer) args[i];
                break;
            }
        }

        if (organisationId == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return organisationId;
    }
}