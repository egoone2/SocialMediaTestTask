package ru.osokin.tasklist.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.osokin.tasklist.domain.Post;
import ru.osokin.tasklist.service.PostService;
import ru.osokin.tasklist.service.UserService;
import ru.osokin.tasklist.service.impl.FileStorageService;
import ru.osokin.tasklist.web.dto.post.PostDto;
import ru.osokin.tasklist.web.response.PostAddedResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper;

    @PostMapping("/add")
    public PostAddedResponse addPost(@RequestParam("file") MultipartFile file,
                                     @RequestParam("post") String post) throws IOException {

        PostDto postDto = objectMapper.readValue(post, PostDto.class);
        String filename = fileStorageService.storeFile(file);
        Post postToCreate = postService.create(postDto.getHeader(), postDto.getContent(), filename);


        return new PostAddedResponse(postToCreate.getHeader(),
                postToCreate.getContent(),
                postToCreate.getAuthor().getUsername(),
                postToCreate.getFilename(),
                file.getSize(),
                postToCreate.getWhenPosted());
    }

}
