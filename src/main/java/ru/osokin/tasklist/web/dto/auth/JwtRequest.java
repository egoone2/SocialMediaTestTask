package ru.osokin.tasklist.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Запрос на логин")
public class JwtRequest {
    @NotNull(message = "Username must be not null")
    @Schema(description = "Имя пользователя (НЕ email)")
    private String username;
    @NotNull(message = "Password must be not null")
    @Schema(description = "Пароль")
    private String password;
}
