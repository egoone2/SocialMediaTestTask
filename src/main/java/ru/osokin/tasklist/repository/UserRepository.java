package ru.osokin.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.osokin.tasklist.domain.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

//    Optional<User> findById(Long id);
//
//
//    void update(User user);
//
//    void create(User user);
//
//    void insertUserRole(Long userId, Role role);
//
//    boolean isTaskOwner(Long userId, Long taskId);
//
//    void delete(Long id);
}
