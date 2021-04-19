package com.nelioalves.cursomc.domain.service;

import com.nelioalves.cursomc.core.security.UserSecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public static UserSecurityService authenticated() {
        try {
            return (UserSecurityService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}