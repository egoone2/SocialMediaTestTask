package ru.osokin.tasklist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.osokin.tasklist.config.AppConstants;
import ru.osokin.tasklist.domain.Post;
import ru.osokin.tasklist.domain.exception.ResourceNotFoundException;
import ru.osokin.tasklist.domain.user.User;
import ru.osokin.tasklist.repository.PostRepository;
import ru.osokin.tasklist.service.PostService;
import ru.osokin.tasklist.service.UserService;
import ru.osokin.tasklist.web.dto.mappers.PostConverter;
import ru.osokin.tasklist.web.dto.post.PostDto;
import ru.osokin.tasklist.web.security.JwtEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final PostConverter postConverter;


    @Override
    public List<PostDto> getAllPostsById(Long id) {
        return postRepository.findPostsByAuthorId(id).stream()
                .map(postConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Post getById(Long id) {

        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    @Override
    @Transactional
    public PostDto create(String header, String content, String filename) {
        JwtEntity userDetails = (JwtEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = new Post();
        post.setHeader(header);
        post.setContent(content);
        post.setWhenPosted(LocalDateTime.now());
        post.setAuthor(userService.getByUsername(userDetails.getUsername()));
        post.setFilename(filename);
        postRepository.save(post);
        return postConverter.toDto(post);
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
    public void update(Post postToUpdate, PostDto postDto) {
        if (!postDto.getFileName().isEmpty())
            postToUpdate.setFilename(postDto.getFileName());

        postToUpdate.setHeader(postDto.getHeader());
        postToUpdate.setContent(postDto.getContent());
        postRepository.save(postToUpdate);
    }


    @Override
    public List<PostDto> getAllSubscriptionsPosts(int pageNo, int pageSize, String sortDir) {
        User currentUser = getCurrentUser();

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(AppConstants.DEFAULT_POSTS_SORT_PROPERTY).ascending()
                : Sort.by(AppConstants.DEFAULT_POSTS_SORT_PROPERTY).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAllSubscriptionsPosts(currentUser.getId(), pageable);


        return posts.getContent().stream()
                .map(postConverter::toDto)
                .collect(Collectors.toList());
    }

    private User getCurrentUser() {
        JwtEntity userDetails = (JwtEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getById(userDetails.getId());
    }
}
