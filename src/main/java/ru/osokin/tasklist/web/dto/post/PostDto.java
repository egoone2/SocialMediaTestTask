package ru.osokin.tasklist.web.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.osokin.tasklist.web.dto.validation.OnCreate;
import ru.osokin.tasklist.web.dto.validation.OnUpdate;

import java.time.LocalDateTime;

@Data
@Schema(description = "DTO for publication entity")
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @Schema(description = "ID")
    private Long id;
    @Length(max = 50, message = "header length must be shorter than 50", groups = {OnCreate.class, OnUpdate.class})
    @Schema(description = "Publication header")
    private String header;

    @Length(max = 255, message = "content length must be shorter than 255", groups = {OnCreate.class, OnUpdate.class})
    @Schema(description = "Text content")
    private String content;
    @Schema(description = "Author's username")
    private String authorUsername;
    @Schema(description = "Filename")
    private String fileName;
    @Schema(description = "Timestamp when posted")
    private LocalDateTime whenPosted;

    public PostDto(String header, String content, String fileName) {
        this.header = header;
        this.content = content;
        this.fileName = fileName;
    }

}
