package ru.osokin.tasklist.web.controller.meta;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/subs")
@Tag(name = "Subscriptions", description = "Subscribe & unsubscribe")
@SecurityRequirement(name = "JWT")
public interface SubscriptionControllerMeta {

    @PatchMapping("/{id}/subscribe")
    @Operation(summary = "Subscribe", description = "Subscribe to chosen user")
    ResponseEntity<?> subscribe(@PathVariable("id")
                                @Parameter(description = "User ID to subscribe") Long userToSubscribeId);

    @PatchMapping("/{id}/unsubscribe")
    @Operation(summary = "Unsubscribe", description = "Unsubscribe from chosen user")
    ResponseEntity<?> unsubscribe(@PathVariable("id")
                                  @Parameter(description = "User ID to unsubscribe") Long userToUnsubscribeId);
}
