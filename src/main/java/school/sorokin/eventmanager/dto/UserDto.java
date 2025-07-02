package school.sorokin.eventmanager.dto;

import school.sorokin.eventmanager.model.user.UserRole;

public record UserDto(
        Long id,
        String login,
        String passwordHash,
        Integer age,
        UserRole role
) {
}
