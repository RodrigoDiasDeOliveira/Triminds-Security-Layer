package com.triminds.security.intelligence;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/intelligence")
public class IntelligenceController {

    private final TelemetryService telemetryService;

    public IntelligenceController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @GetMapping("/events")
    public String events() {
        return telemetryService.getEventSummary();
    }
}
