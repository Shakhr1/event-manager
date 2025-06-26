package school.sorokin.eventmanager.mapper;

import org.mapstruct.*;
import school.sorokin.eventmanager.dto.SignUpRequest;
import school.sorokin.eventmanager.dto.UserDto;
import school.sorokin.eventmanager.model.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(target = "passwordHash", source = "password")
    User toDomain(SignUpRequest signUpRequest);

    UserDto toDto(User user);
}
