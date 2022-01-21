package ch.bbcag.controllers;

import ch.bbcag.models.ApplicationUser;
import ch.bbcag.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    public ApplicationUser getCurrentUser() {
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = applicationUserRepository.findByUsername(userName);
        if (user == null) {
            throw new IllegalStateException("call getCurrentUser() but user is null");
        }
        return user;
    }
}
