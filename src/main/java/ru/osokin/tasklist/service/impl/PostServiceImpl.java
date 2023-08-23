package ru.osokin.tasklist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.osokin.tasklist.domain.Post;
import ru.osokin.tasklist.domain.exception.ResourceNotFoundException;
import ru.osokin.tasklist.domain.user.User;
import ru.osokin.tasklist.repository.PostRepository;
import ru.osokin.tasklist.service.PostService;
import ru.osokin.tasklist.service.UserService;
import ru.osokin.tasklist.web.security.JwtEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;


    @Override
    public List<Post> getAllPostsById(Long id) {
        return postRepository.findPostsByAuthorId(id);
    }

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    @Override
    @Transactional
    public Post create(String header, String content, String filename) {
        JwtEntity userDetails = (JwtEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
    @Transactional
    public void delete(Long id) {
        if (postRepository.findById(id).isEmpty())
            throw new IllegalStateException("Post you are trying to delete does not exist.");

        postRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Post post) {
        postRepository.save(post);
    }


    @Override
    public List<Post> getAllSubscriptionsPosts(User user) {
        List<Post> posts = new ArrayList<>();

        user.getSubscriptions().forEach(u -> {
            posts.addAll(getAllPostsById(u.getId()));
        });

        return posts;
    }
}
