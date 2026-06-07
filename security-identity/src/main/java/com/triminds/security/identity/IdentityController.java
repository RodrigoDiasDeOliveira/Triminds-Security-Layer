package com.triminds.security.identity;

import com.triminds.security.shared.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/identity")
public class IdentityController {

    private final IdentityService identityService;

    public IdentityController(IdentityService identityService) {
        this.identityService = identityService;
    }

    @GetMapping("/me")
    public User getCurrentUser() {
        return identityService.getCurrentUser();
    }

    @GetMapping("/roles")
    public Set<String> getRoles() {
        return identityService.getAvailableRoles();
    }
}
