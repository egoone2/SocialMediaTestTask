package ru.osokin.tasklist.web.dto.mappers;

import org.mapstruct.Mapper;
import ru.osokin.tasklist.domain.user.User;
import ru.osokin.tasklist.web.dto.user.UserDto;

@Mapper(componentModel = "spring")
public class UserMapper {

    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setEmail( user.getEmail() );
        userDto.setUsername( user.getUsername() );
        userDto.setPassword( user.getPassword() );

        return userDto;
    }

    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setId( dto.getId() );
        user.setEmail( dto.getEmail() );
        user.setUsername( dto.getUsername() );
        user.setPassword( dto.getPassword() );

        return user;
    }

}
