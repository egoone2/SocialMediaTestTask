package ru.osokin.tasklist.service;

import ru.osokin.tasklist.domain.Post;
import ru.osokin.tasklist.domain.user.User;

import java.util.List;

public interface PostService {

    List<Post> getAllPostsById(Long id);

    Post getById(Long id);

    Post create(String header, String content, String filename);

    void delete(Long id);

    void update(Post post);

    List<Post> getAllSubscriptionsPosts(User user);
}
