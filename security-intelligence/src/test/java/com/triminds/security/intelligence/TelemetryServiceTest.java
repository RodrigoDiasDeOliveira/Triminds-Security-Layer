package com.triminds.security.intelligence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TelemetryServiceTest {

    @Test
    void getEventSummaryReturnsActive() {
        TelemetryService service = new TelemetryService();
        assertEquals("Intelligence event stream is active", service.getEventSummary());
    }
}
