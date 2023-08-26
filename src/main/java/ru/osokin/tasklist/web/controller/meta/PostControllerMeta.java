package ru.osokin.tasklist.web.controller.meta;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.osokin.tasklist.web.dto.post.PostDto;
import ru.osokin.tasklist.web.response.PostAddedResponse;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "Publications")
public interface PostControllerMeta {

    @PostMapping("/add")
    @Operation(summary = "Add publication")
    @SecurityRequirement(name = "JWT")
    PostAddedResponse addPost(@RequestPart("file") @Parameter(description = "File to upload (up to 200 MB)") MultipartFile file,
                              @RequestPart("header") @Parameter(description = "Header of publication") String header,
                              @RequestPart("content") @Parameter(description = "Text content of publication") String content);

    @PatchMapping("/{id}")
    @Operation(summary = "Update publication")
    @SecurityRequirement(name = "JWT")
    PostDto editPost(@PathVariable("id") @Parameter(description = "Publication ID") Long id,
                  @RequestPart("file") @Parameter(description = "File to upload (up to 200 MB)") MultipartFile file,
                  @RequestPart("header") @Parameter(description = "Header of publication") String header,
                  @RequestPart("content") @Parameter(description = "Text content of publication") String content);


    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    ResponseEntity<HttpStatus> deletePost(@PathVariable("id") @Parameter(description = "Publication ID") Long id);
}
