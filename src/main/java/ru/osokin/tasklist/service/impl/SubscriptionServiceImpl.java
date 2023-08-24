package ru.osokin.tasklist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.osokin.tasklist.domain.exception.ResourceNotFoundException;
import ru.osokin.tasklist.domain.user.User;
import ru.osokin.tasklist.repository.UserRepository;
import ru.osokin.tasklist.service.SubscriptionService;
import ru.osokin.tasklist.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void subscribe(User currentUser, Long userToSubscribeId) {
        User userToSubscribe = getUserToCheckOrSubscribeById(userToSubscribeId);
        userToSubscribe.getSubscribers().add(currentUser);
        currentUser.getSubscriptions().add(userToSubscribe);

        userRepository.saveAll(List.of(userToSubscribe, currentUser));
    }

    @Override
    @Transactional
    public void unsubscribe(User currentUser, Long userToUnsubscribeId) {
        User userToUnsubscribe = getUserToCheckOrSubscribeById(userToUnsubscribeId);

        userToUnsubscribe.getSubscribers().remove(currentUser);
        currentUser.getSubscriptions().remove(userToUnsubscribe);

        userRepository.saveAll(List.of(userToUnsubscribe, currentUser));
    }

    @Override
    public boolean checkIfSubscribed(User currentUser, Long userToCheckId) {
        User userToCheck = getUserToCheckOrSubscribeById(userToCheckId);

        return currentUser.getSubscriptions().contains(userToCheck);
    }

    private User getUserToCheckOrSubscribeById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User you are trying to unsubscribe does not exist."));
    }
}
