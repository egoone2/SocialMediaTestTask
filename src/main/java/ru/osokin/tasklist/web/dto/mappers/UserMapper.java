package ru.osokin.tasklist.web.dto.mappers;

import org.mapstruct.Mapper;
import ru.osokin.tasklist.domain.user.User;
import ru.osokin.tasklist.web.dto.user.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto dto);
}
