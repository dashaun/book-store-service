package com.vinc.bookstoreservice.exceptions;

import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

public class AuthorNotFoundException extends ErrorResponseException {

    public AuthorNotFoundException(String id) {
        super(NOT_FOUND, asProblemDetail("Author with id: " + id + " not found"), null);
    }

    private static ProblemDetail asProblemDetail(String message) {
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, message);
        problemDetail.setTitle("Author Not Found");
        problemDetail.setType(URI.create("http://localhost:8083/api/1/authors/"));
        problemDetail.setProperty("errorCategory", "Custom handled error");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
