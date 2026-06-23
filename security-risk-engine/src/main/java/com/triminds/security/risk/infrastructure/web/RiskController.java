package com.triminds.security.risk.infrastructure.web;

import com.triminds.security.risk.application.usecase.ScoreRiskUseCase;
import com.triminds.security.risk.domain.RiskScore;
import com.triminds.security.risk.domain.RiskSignal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/risk")
@RequiredArgsConstructor
public class RiskController {
    private final ScoreRiskUseCase score;

    @PostMapping("/score")
    public ResponseEntity<RiskScore> score(@Valid @RequestBody RiskSignal s) {
        return ResponseEntity.ok(score.score(s));
    }
}
