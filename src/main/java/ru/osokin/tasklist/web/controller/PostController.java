package ru.osokin.tasklist.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.osokin.tasklist.domain.Post;
import ru.osokin.tasklist.domain.exception.AccessDeniedException;
import ru.osokin.tasklist.domain.exception.FileStorageException;
import ru.osokin.tasklist.service.PostService;
import ru.osokin.tasklist.service.impl.FileStorageService;
import ru.osokin.tasklist.web.dto.post.PostDto;
import ru.osokin.tasklist.web.response.PostAddedResponse;
import ru.osokin.tasklist.web.security.JwtEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "Посты (публикации)")

public class PostController {

    private final PostService postService;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper;

    @PostMapping("/add")
    @Operation(summary = "Добавление публикации", description = "Позволяет добваить пост")
    @SecurityRequirement(name = "JWT")
    public PostAddedResponse addPost(@RequestPart("file") @Parameter(description = "Файл для загрузки (до 200 МБ)") MultipartFile file,
                                     @RequestPart("post") @Parameter(description = "Сущность PostDto в формате Json") String postJson) throws JsonProcessingException {

        PostDto postDto = objectMapper.readValue(postJson, PostDto.class);
        String filename = fileStorageService.storeFile(file);
        Post postToCreate = postService.create(postDto.getHeader(), postDto.getContent(), filename);


        return new PostAddedResponse(postToCreate.getHeader(),
                postToCreate.getContent(),
                postToCreate.getAuthor().getUsername(),
                postToCreate.getFilename(),
                file.getSize(),
                postToCreate.getWhenPosted());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(FileStorageException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Публикация")
    public Post publication(@PathVariable("id") @Parameter(description = "Идентификатор публикации") Long id) {
        return postService.getById(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Изменение публикации", description = "Позволяет изменить пост")
    @SecurityRequirement(name = "JWT")
    public Post editPost(@PathVariable("id") @Parameter(description = "Идентификатор публикации") Long id,
                         @RequestPart("post") @Parameter(description = "Сущность PostDto в формате Json") String postJson,
                         @RequestPart("file") @Parameter(description = "Файл для загрузки (до 200 МБ)") MultipartFile file) throws JsonProcessingException {
        Post postToUpdate = checkIfCurrentUser(id);

        PostDto postDto = objectMapper.readValue(postJson, PostDto.class);
        String filename = fileStorageService.storeFile(file);

        postToUpdate.setHeader(postDto.getHeader());
        postToUpdate.setContent(postDto.getContent());
        if (!filename.isEmpty())
            postToUpdate.setFilename(filename);

        postService.update(postToUpdate);
        return postToUpdate;
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable("id") @Parameter(description = "Идентификатор публикации") Long id) {
        checkIfCurrentUser(id);

        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Post checkIfCurrentUser(Long id) {
        JwtEntity userDetails = (JwtEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post postToBeChanged = postService.getById(id);
        if (!postToBeChanged.getAuthor().getId().equals(userDetails.getId()))
            throw new AccessDeniedException();
        return postToBeChanged;
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(AccessDeniedException e) {
        return new ResponseEntity<>("Access denied. You can not edit or delete other's posts.", HttpStatus.FORBIDDEN);
    }


}
