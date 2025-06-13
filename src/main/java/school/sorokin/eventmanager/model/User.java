package school.sorokin.eventmanager.model;

public record User(
        Long id,
        String login,
        String passwordHash,
        Integer age,
        UserRole role
) {
}
