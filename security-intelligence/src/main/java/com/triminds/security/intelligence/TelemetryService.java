package com.triminds.security.intelligence;

import org.springframework.stereotype.Service;

@Service
public class TelemetryService {

    public String getEventSummary() {
        return "Intelligence event stream is active";
    }
}
