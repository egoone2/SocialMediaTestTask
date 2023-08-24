package ru.osokin.tasklist.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.osokin.tasklist.config.AppConstants;
import ru.osokin.tasklist.domain.Post;
import ru.osokin.tasklist.domain.exception.ResourceNotFoundException;
import ru.osokin.tasklist.domain.user.User;
import ru.osokin.tasklist.service.PostService;
import ru.osokin.tasklist.service.UserService;
import ru.osokin.tasklist.web.dto.mappers.UserMapper;
import ru.osokin.tasklist.web.dto.user.UserDto;
import ru.osokin.tasklist.web.dto.validation.OnUpdate;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final UserMapper userMapper;


    @PutMapping
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto dto) {
        User userToUpdate = userMapper.toEntity(dto);
        User updatedUser = userService.update(userToUpdate);
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/{id}/posts")
    public List<Post> getAllPosts(@PathVariable("id") Long id) {
        return postService.getAllPostsById(id);
    }

    @GetMapping("/feed")
    public List<Post> getAllFeedPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                      @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

        return postService.getAllSubscriptionsPosts(pageNo, pageSize, sortDir);
    }


    @ExceptionHandler
    public ResponseEntity<?> handleException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(IllegalStateException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
