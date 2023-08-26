package ru.osokin.tasklist.web.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.osokin.tasklist.web.dto.validation.OnCreate;
import ru.osokin.tasklist.web.dto.validation.OnUpdate;

@Data
@Schema(description = "Сущность поста (публикации) для создания")
public class PostDto {

    @Length(max = 50, message = "header length must be shorter than 50", groups = {OnCreate.class, OnUpdate.class})
    @Schema(description = "Заголовок поста")
    private String header;

    @Length(max = 255, message = "content length must be shorter than 255", groups = {OnCreate.class, OnUpdate.class})
    @Schema(description = "Текст публикации")
    private String content;


}
