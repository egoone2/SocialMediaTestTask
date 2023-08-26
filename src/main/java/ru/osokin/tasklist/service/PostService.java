package ru.osokin.tasklist.service;

import ru.osokin.tasklist.domain.Post;
import ru.osokin.tasklist.web.dto.post.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPostsById(Long id);

    Post getById(Long id);

    PostDto create(String header, String content, String filename);

    void delete(Long id);

    void update(Post post, PostDto postDto);

    List<PostDto> getAllSubscriptionsPosts(int pageNo, int pageSize, String sortDir);




}
