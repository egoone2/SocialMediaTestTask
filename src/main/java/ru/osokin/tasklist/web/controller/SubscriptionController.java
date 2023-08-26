package ru.osokin.tasklist.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import ru.osokin.tasklist.domain.user.User;
import ru.osokin.tasklist.service.SubscriptionService;
import ru.osokin.tasklist.service.UserService;
import ru.osokin.tasklist.web.controller.meta.SubscriptionControllerMeta;

@RequiredArgsConstructor
public class SubscriptionController implements SubscriptionControllerMeta {

    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @PatchMapping("/{id}/subscribe")
    public ResponseEntity<?> subscribe(Long userToSubscribeId) {
        User currentUser = getCurrentUser();

        subscriptionService.subscribe(currentUser, userToSubscribeId);
        return ResponseEntity.ok("Successful subscribe");
    }

    @PatchMapping("/{id}/unsubscribe")
    public ResponseEntity<?> unsubscribe(Long userToUnsubscribeId) {
        User currentUser = getCurrentUser();

        subscriptionService.unsubscribe(currentUser, userToUnsubscribeId);
        return ResponseEntity.ok("Successful unsubscribe");
    }


    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getByUsername(userDetails.getUsername());
    }
}
