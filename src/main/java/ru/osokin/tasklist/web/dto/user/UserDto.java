package ru.osokin.tasklist.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.osokin.tasklist.web.dto.validation.OnCreate;
import ru.osokin.tasklist.web.dto.validation.OnUpdate;

@Data
@NoArgsConstructor
public class UserDto {
    @NotNull(message = "Id must be not null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "Email must not be not null", groups = {OnCreate.class, OnUpdate.class})
    @Pattern(regexp = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+")
    @Length(max = 255, message = "Name length must be shorter than 255", groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @NotNull(message = "Username must not be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "username length must be shorter than 255", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must not be null", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Confirmation must not be null", groups = {OnCreate.class})
    private String passwordConfirmation;

}
