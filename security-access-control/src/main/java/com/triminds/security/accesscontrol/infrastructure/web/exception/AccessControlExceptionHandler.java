package com.triminds.security.accesscontrol.infrastructure.web.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class AccessControlExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handle(RuntimeException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(403);

        problem.setTitle("Access Control Error");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://triminds/security/access-control/error"));

        return problem;
    }
}