package ru.osokin.tasklist.service;

import ru.osokin.tasklist.domain.user.User;

public interface SubscriptionService {

    void subscribe(User currentUser, Long userToSubscribeId);

    void unsubscribe(User currentUser, Long userToUnsubscribeId);

    boolean checkIfSubscribed(User currentUser, Long userToCheckId);

}
