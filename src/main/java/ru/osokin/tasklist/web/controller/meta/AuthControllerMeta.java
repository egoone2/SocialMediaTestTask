package ru.osokin.tasklist.web.controller.meta;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.osokin.tasklist.web.dto.auth.JwtRequest;
import ru.osokin.tasklist.web.dto.auth.JwtResponse;
import ru.osokin.tasklist.web.dto.user.UserDto;
import ru.osokin.tasklist.web.dto.validation.OnCreate;


@RestController
@RequestMapping("/api/v1/auth")
@Validated
@Tag(name = "Authentication", description = "Controller for JWT authentication")
public interface AuthControllerMeta {
    @PostMapping("/login")
    JwtResponse login(@Validated @RequestBody JwtRequest loginRequest);

    @PostMapping("/register")
    UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto);

    @PostMapping("/refresh")
    JwtResponse refresh(@RequestBody String refreshToken);
}
