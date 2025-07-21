package com.school_medical.school_medical_management_system.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {
    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG: Authentication = " + authentication);
        if (authentication == null) {
            System.out.println("DEBUG: No authentication found!");
            return null;
        }
        System.out.println("DEBUG: Principal = " + authentication.getPrincipal());
        System.out.println("DEBUG: Name = " + authentication.getName());
        return authentication.getName();
    }
}
