package ru.osokin.tasklist.service;

import ru.osokin.tasklist.domain.Post;

import java.util.List;

public interface PostService {

    List<Post> getAllByUserId(Long id);

    Post getById(Long id);

    Post create(String header, String content, String filename);

    void delete(Long id);

    void update(Post post);
}
