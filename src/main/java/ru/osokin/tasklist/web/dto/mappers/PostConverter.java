package ru.osokin.tasklist.web.dto.mappers;

import org.springframework.stereotype.Component;
import ru.osokin.tasklist.domain.Post;
import ru.osokin.tasklist.web.dto.post.PostDto;

@Component
public class PostConverter {

    public PostDto toDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setHeader(post.getHeader());
        dto.setContent(post.getContent());
        dto.setAuthorUsername(post.getAuthor().getUsername());
        dto.setWhenPosted(post.getWhenPosted());
        return dto;
    }
}
