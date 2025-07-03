package school.sorokin.eventmanager.model.user;

public record User(
        Long id,
        String login,
        String passwordHash,
        Integer age,
        UserRole role
) {
}
