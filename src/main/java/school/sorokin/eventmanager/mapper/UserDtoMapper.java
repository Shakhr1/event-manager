package school.sorokin.eventmanager.mapper;

import org.springframework.stereotype.Component;
import school.sorokin.eventmanager.dto.UserDto;
import school.sorokin.eventmanager.model.User;

@Component
public class UserDtoMapper {
    public User toDomain(UserDto userDto) {
        return new User(
                userDto.id(),
                userDto.login(),
                userDto.passwordHash(),
                userDto.age(),
                userDto.role()
        );
    }

    public UserDto toDto(User user) {
        return new UserDto(
                user.id(),
                user.login(),
                user.passwordHash(),
                user.age(),
                user.role()
        );
    }
}
