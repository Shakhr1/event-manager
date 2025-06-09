package school.sorokin.eventmanager.model;

public record Location(
        Long id,
        String name,
        String address,
        Long capacity,
        String description) {
}
