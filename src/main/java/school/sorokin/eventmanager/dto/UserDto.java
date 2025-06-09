package school.sorokin.eventmanager.dto;

import school.sorokin.eventmanager.model.UserRole;

public record UserDto(
        Long id,
        String login,
        String passwordHash,
        Integer age,
        UserRole role
) {
}
