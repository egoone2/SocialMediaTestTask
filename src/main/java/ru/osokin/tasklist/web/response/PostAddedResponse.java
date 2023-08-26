package ru.osokin.tasklist.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ после добавления поста")
public class PostAddedResponse {

    private String header;
    private String content;
    @Schema(description = "Имя пользователя автора")
    private String authorUsername;
    @Schema(description = "Имя файла")
    private String fileName;
    @Schema(description = "Размер файла")
    private long size;
    @Schema(description = "Время публикации")
    private LocalDateTime whenAdded;

}
