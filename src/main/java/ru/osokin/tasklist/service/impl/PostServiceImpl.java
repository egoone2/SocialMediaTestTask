package ru.osokin.tasklist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.osokin.tasklist.domain.Post;
import ru.osokin.tasklist.repository.PostRepository;
import ru.osokin.tasklist.service.PostService;
import ru.osokin.tasklist.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;



    @Override
    public List<Post> getAllByUserId(Long id) {
        return null;
    }

    @Override
    public Post getById(Long id) {
        return null;
    }

    @Override
    public Post create(String header, String content, String filename) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = new Post();
        post.setHeader(header);
        post.setContent(content);
        post.setWhenPosted(LocalDateTime.now());
        post.setAuthor(userService.getByUsername(userDetails.getUsername()));
        post.setFilename(filename);
        postRepository.save(post);
        return post;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Post post) {

    }
}
