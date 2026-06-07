package com.triminds.security.accesscontrol;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/access")
public class AccessControlController {

    private final AccessControlService accessControlService;

    public AccessControlController(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }

    @GetMapping("/status")
    public String status() {
        return accessControlService.getStatus();
    }
}
