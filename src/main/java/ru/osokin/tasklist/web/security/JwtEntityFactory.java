package ru.osokin.tasklist.web.security;

import ru.osokin.tasklist.domain.user.User;

import java.util.ArrayList;

public class JwtEntityFactory {

    public static JwtEntity create(User user) {
        return new JwtEntity(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>(user.getRoles())
        );
    }



}
