package ru.osokin.tasklist.service;

import ru.osokin.tasklist.domain.user.User;

import java.util.List;


public interface UserService {

    User getById(Long id);

    User getByUsername(String username);

    User update(User user);

    void updateSubs(List<User> users);

    User create(User user);

    void delete(Long id);

}
