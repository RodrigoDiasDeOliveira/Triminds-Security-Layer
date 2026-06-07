package com.triminds.security.gateway;

import org.springframework.stereotype.Service;

@Service
public class GatewayService {

    public String status() {
        return "Security gateway is intercepting requests";
    }
}
