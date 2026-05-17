package me.bitsandbites.backend.helpers;

import me.bitsandbites.backend.dtos.Role;

public class RoleValidator {
    public static boolean isRoleHigherOrEqual(Role toCheck, Role minimumRole) {
        if (minimumRole.equals(Role.admin) && !toCheck.equals(Role.admin)) {
            return false;
        } else return !minimumRole.equals(Role.trainer) ||
                (toCheck.equals(Role.trainer) || toCheck.equals(Role.admin));
    }
}
