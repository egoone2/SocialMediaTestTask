package ru.osokin.tasklist.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import ru.osokin.tasklist.domain.Post;
import ru.osokin.tasklist.domain.exception.AccessDeniedException;
import ru.osokin.tasklist.service.PostService;
import ru.osokin.tasklist.service.impl.FileStorageService;
import ru.osokin.tasklist.web.controller.meta.PostControllerMeta;
import ru.osokin.tasklist.web.dto.mappers.PostConverter;
import ru.osokin.tasklist.web.dto.post.PostDto;
import ru.osokin.tasklist.web.response.PostAddedResponse;
import ru.osokin.tasklist.web.security.JwtEntity;

@RequiredArgsConstructor
public class PostController implements PostControllerMeta {

    private final PostService postService;
    private final FileStorageService fileStorageService;
    private final PostConverter postConverter;


    @PostMapping("/add")
    public PostAddedResponse addPost(MultipartFile file, String header, String content) {

        String filename = fileStorageService.storeFile(file);
        PostDto postDto = new PostDto(header, content, filename);
        PostDto postToCreate = postService.create(postDto.getHeader(), postDto.getContent(), postDto.getFileName());


        return new PostAddedResponse(postToCreate.getHeader(),
                postToCreate.getContent(),
                postToCreate.getAuthorUsername(),
                postToCreate.getFileName(),
                file.getSize(),
                postToCreate.getWhenPosted());
    }

    @PatchMapping("/{id}")
    public PostDto editPost(Long id, MultipartFile file, String header, String content) {
        Post postToUpdate = checkIfCurrentUserAndReturnPost(id);

        String filename = fileStorageService.storeFile(file);
        PostDto postDto = new PostDto(header, content, filename);


        postService.update(postToUpdate, postDto);
        return postConverter.toDto(postToUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePost(Long id) {
        checkIfCurrentUserAndReturnPost(id);

        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Post checkIfCurrentUserAndReturnPost(Long id) {
        JwtEntity userDetails = (JwtEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post postToBeChanged = postService.getById(id);
        if (!postToBeChanged.getAuthor().getId().equals(userDetails.getId())) {
            throw new AccessDeniedException();
        }
        return postToBeChanged;
    }


}
