package ru.osokin.tasklist.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import ru.osokin.tasklist.domain.user.User;
import ru.osokin.tasklist.service.AuthService;
import ru.osokin.tasklist.service.UserService;
import ru.osokin.tasklist.web.controller.meta.AuthControllerMeta;
import ru.osokin.tasklist.web.dto.auth.JwtRequest;
import ru.osokin.tasklist.web.dto.auth.JwtResponse;
import ru.osokin.tasklist.web.dto.mappers.UserMapper;
import ru.osokin.tasklist.web.dto.user.UserDto;

@RequiredArgsConstructor
public class AuthController implements AuthControllerMeta {

    private final AuthService authService;
    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(UserDto userDto) {

        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
