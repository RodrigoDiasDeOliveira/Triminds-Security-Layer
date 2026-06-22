package com.triminds.security.accesscontrol.test.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccessControlControllerIT {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void shouldCheckPermission() {

        UUID tenantId = UUID.randomUUID();
        UUID identityId = UUID.randomUUID();

        String url = "/access/check"
                + "?identityId=" + identityId
                + "&action=READ"
                + "&resource=orders";

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Tenant-Id", tenantId.toString());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = rest.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}