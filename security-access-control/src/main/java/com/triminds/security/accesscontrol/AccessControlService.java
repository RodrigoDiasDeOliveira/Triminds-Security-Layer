package com.triminds.security.accesscontrol;

import org.springframework.stereotype.Service;

@Service
public class AccessControlService {

    public String getStatus() {
        return "Access control engine is available";
    }
}
