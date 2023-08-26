package ru.osokin.tasklist.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.osokin.tasklist.domain.user.User;
import ru.osokin.tasklist.service.AuthService;
import ru.osokin.tasklist.service.UserService;
import ru.osokin.tasklist.web.dto.auth.JwtRequest;
import ru.osokin.tasklist.web.dto.auth.JwtResponse;
import ru.osokin.tasklist.web.dto.mappers.UserMapper;
import ru.osokin.tasklist.web.dto.user.UserDto;
import ru.osokin.tasklist.web.dto.validation.OnCreate;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Аутентификация", description = "Контроллер для JWT аутентификации")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation()))
            throw new IllegalStateException("Password and password confirmation do not match.");

        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(IllegalStateException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
