package ru.osokin.tasklist.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.osokin.tasklist.domain.user.User;
import ru.osokin.tasklist.service.SubscriptionService;
import ru.osokin.tasklist.service.UserService;

@RestController
@RequestMapping("/api/v1/subs")
@RequiredArgsConstructor
public class SubscriptionController {

    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @PatchMapping("/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable("id") Long userToSubscribeId) {
        // TODO: проверка на наличие подписки

        subscriptionService.subscribe(getCurrentUser(), userToSubscribeId);
        return ResponseEntity.ok("Successful subscribe");
    }

    @PatchMapping("/{id}/unsubscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") Long userToUnsubscribeId) {
        // TODO: проверка на наличие подписки

        subscriptionService.unsubscribe(getCurrentUser(), userToUnsubscribeId);
        return ResponseEntity.ok("Successful unsubscribe");
    }

    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getByUsername(userDetails.getUsername());
    }
}
