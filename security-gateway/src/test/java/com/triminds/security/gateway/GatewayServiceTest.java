package com.triminds.security.gateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GatewayServiceTest {

    @Test
    void statusReturnsIntercepting() {
        GatewayService service = new GatewayService();
        assertEquals("Security gateway is intercepting requests", service.status());
    }
}
