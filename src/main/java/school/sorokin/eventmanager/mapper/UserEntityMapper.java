package school.sorokin.eventmanager.mapper;

import org.mapstruct.Mapper;
import school.sorokin.eventmanager.entity.UserEntity;
import school.sorokin.eventmanager.model.User;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity toEntity(User user);

    User toDomain(UserEntity entity);
}
