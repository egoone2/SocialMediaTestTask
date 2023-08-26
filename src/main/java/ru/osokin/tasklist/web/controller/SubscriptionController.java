package ru.osokin.tasklist.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.osokin.tasklist.domain.exception.ResourceNotFoundException;
import ru.osokin.tasklist.domain.exception.SubscriptionException;
import ru.osokin.tasklist.domain.user.User;
import ru.osokin.tasklist.service.SubscriptionService;
import ru.osokin.tasklist.service.UserService;

@RestController
@RequestMapping("/api/v1/subs")
@RequiredArgsConstructor
@Tag(name = "Подписки", description = "Подписка и отписка")
@SecurityRequirement(name = "JWT")
public class SubscriptionController {

    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @PatchMapping("/{id}/subscribe")
    @Operation(summary = "Подписаться", description = "Позволяет подписаться на выбранного пользователя")
    public ResponseEntity<?> subscribe(@PathVariable("id")
                                       @Parameter(description = "Идентификатор пользователя, на которого нужно подписаться") Long userToSubscribeId) {
        User currentUser = getCurrentUser();
        if (subscriptionService.checkIfSubscribed(currentUser, userToSubscribeId))
            throw new SubscriptionException("You have already subscribed on this person");

        subscriptionService.subscribe(currentUser, userToSubscribeId);
        return ResponseEntity.ok("Successful subscribe");
    }

    @PatchMapping("/{id}/unsubscribe")
    @Operation(summary = "Отписаться", description = "Позволяет отписаться от выбранного пользователя")
    public ResponseEntity<?> unsubscribe(@PathVariable("id")
                                         @Parameter(description = "Идентификатор пользователя, от которого нужно отписаться") Long userToUnsubscribeId) {
        User currentUser = getCurrentUser();
        if (!subscriptionService.checkIfSubscribed(currentUser, userToUnsubscribeId))
            throw new SubscriptionException("You are not subscribed on this person");


        subscriptionService.unsubscribe(currentUser, userToUnsubscribeId);
        return ResponseEntity.ok("Successful unsubscribe");
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(SubscriptionException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getByUsername(userDetails.getUsername());
    }
}
