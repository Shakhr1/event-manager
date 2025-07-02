package school.sorokin.eventmanager.model.location;

public record Location(
        Long id,
        String name,
        String address,
        Long capacity,
        String description) {
}
