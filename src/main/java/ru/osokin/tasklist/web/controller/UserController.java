package ru.osokin.tasklist.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.osokin.tasklist.domain.user.User;
import ru.osokin.tasklist.service.PostService;
import ru.osokin.tasklist.service.UserService;
import ru.osokin.tasklist.web.controller.meta.UserControllerMeta;
import ru.osokin.tasklist.web.dto.mappers.UserMapper;
import ru.osokin.tasklist.web.dto.post.PostDto;
import ru.osokin.tasklist.web.dto.user.UserDto;

import java.util.List;

@RequiredArgsConstructor
public class UserController implements UserControllerMeta {

    private final UserService userService;
    private final PostService postService;
    private final UserMapper userMapper;


    @PutMapping
    public UserDto update(UserDto dto) {
        User userToUpdate = userMapper.toEntity(dto);
        User updatedUser = userService.update(userToUpdate);
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}/posts")
    public List<PostDto> getAllPosts(Long id) {
        return postService.getAllPostsById(id);
    }

    @GetMapping("/feed")
    public List<PostDto> getAllFeedPosts(int pageNo, int pageSize, String sortDir) {
        return postService.getAllSubscriptionsPosts(pageNo, pageSize, sortDir);
    }


}
