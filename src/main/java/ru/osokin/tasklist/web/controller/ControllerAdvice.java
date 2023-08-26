package ru.osokin.tasklist.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.osokin.tasklist.domain.exception.AccessDeniedException;
import ru.osokin.tasklist.domain.exception.FileStorageException;
import ru.osokin.tasklist.domain.exception.ResourceNotFoundException;
import ru.osokin.tasklist.domain.exception.SubscriptionException;
import ru.osokin.tasklist.web.response.ErrorResponse;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(FileStorageException.class)
    public ErrorResponse handleFileStorage(FileStorageException e) {
        return new ErrorResponse("File storage error", e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDenied(AccessDeniedException e) {
        return new ErrorResponse("Access denied", e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleResourceNotFound(ResourceNotFoundException e) {
        return new ErrorResponse("There is no user you're trying to subscribe to", e.getMessage());
    }

    @ExceptionHandler(SubscriptionException.class)
    public ErrorResponse handleSubscription(SubscriptionException e) {
        return new ErrorResponse("Subscription error", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleIllegalState(IllegalStateException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
