package school.sorokin.eventmanager.mapper;

import org.springframework.stereotype.Component;
import school.sorokin.eventmanager.entity.UserEntity;
import school.sorokin.eventmanager.model.User;
import school.sorokin.eventmanager.model.UserRole;

@Component
public class UserEntityMapper {
    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.id(),
                user.login(),
                user.passwordHash(),
                user.age(),
                user.role().name()
        );
    }

    public User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getLogin(),
                entity.getPasswordHash(),
                entity.getAge(),
                UserRole.valueOf(entity.getRole())
        );
    }
}
