package school.sorokin.eventmanager.mapper;

import org.mapstruct.Mapper;
import school.sorokin.eventmanager.dto.SignUpRequest;
import school.sorokin.eventmanager.dto.UserDto;
import school.sorokin.eventmanager.model.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    User toDomain(SignUpRequest signUpRequest);

    UserDto toDto(User user);
}
