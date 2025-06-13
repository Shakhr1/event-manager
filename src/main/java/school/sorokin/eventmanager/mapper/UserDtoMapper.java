package school.sorokin.eventmanager.mapper;

import org.springframework.stereotype.Component;
import school.sorokin.eventmanager.dto.SignUpRequest;
import school.sorokin.eventmanager.dto.UserDto;
import school.sorokin.eventmanager.model.User;

@Component
public class UserDtoMapper {
    public User toDomain(SignUpRequest signUpRequest) {
        return new User(
                null,
                signUpRequest.login(),
                signUpRequest.password(),
                signUpRequest.age(),
                null
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
