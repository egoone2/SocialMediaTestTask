package ru.osokin.tasklist.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Ответ на регистрацию")
public class JwtResponse {

    private Long id;
    private String username;
    private String accessToken;
    private String refreshToken;


}
