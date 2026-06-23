package com.triminds.security.policy.infrastructure.opa;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "opa", url = "${opa.url}")
public interface OpaClient {

    @PostMapping("/v1/data/triminds/allow")
    OpaQueryResponse queryAllow(@RequestBody Map<String, Object> input);

    @PutMapping(value = "/v1/policies/{id}", consumes = "text/plain")
    void putPolicy(@PathVariable("id") String id, @RequestBody String rego);

    @DeleteMapping("/v1/policies/{id}")
    void deletePolicy(@PathVariable("id") String id);
}
