package ru.osokin.tasklist.web.controller.meta;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.osokin.tasklist.config.AppConstants;
import ru.osokin.tasklist.web.dto.post.PostDto;
import ru.osokin.tasklist.web.dto.user.UserDto;
import ru.osokin.tasklist.web.dto.validation.OnUpdate;

import java.util.List;



@RestController
@RequestMapping("/api/v1/users")
@Validated
@Tag(name = "Users")
public interface UserControllerMeta {

    @PutMapping
    @Operation(summary = "Update data", description = "Update current user's data")
    @SecurityRequirement(name = "JWT")
    UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto dto);

    @GetMapping("/{id}/posts")
    @SecurityRequirement(name = "JWT")
    List<PostDto> getAllPosts(@PathVariable("id") @Parameter(description = "User ID") Long id);

    @GetMapping("/feed")
    @SecurityRequirement(name = "JWT")
    List<PostDto> getAllFeedPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                                      @Parameter(description = "Page number") int pageNo,
                               @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                                      @Parameter(description = "Page size") int pageSize,
                               @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                                      @Parameter(description = "Sorting direction (asc/desc)") String sortDir);
}
