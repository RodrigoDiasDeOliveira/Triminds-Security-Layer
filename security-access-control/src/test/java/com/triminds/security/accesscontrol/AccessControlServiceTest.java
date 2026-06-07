package com.triminds.security.accesscontrol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccessControlServiceTest {

    @Test
    void statusReturnsAvailableMessage() {
        AccessControlService service = new AccessControlService();
        assertEquals("Access control engine is available", service.getStatus());
    }
}
