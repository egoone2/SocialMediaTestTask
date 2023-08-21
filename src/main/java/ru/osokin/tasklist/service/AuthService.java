package ru.osokin.tasklist.service;

import ru.osokin.tasklist.web.dto.auth.JwtRequest;
import ru.osokin.tasklist.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);
}
