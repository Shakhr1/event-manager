package school.sorokin.eventmanager.mapper;

import org.springframework.stereotype.Component;
import school.sorokin.eventmanager.entity.LocationEntity;
import school.sorokin.eventmanager.model.Location;

@Component
public class LocationEntityMapper {

    public Location toDomain(LocationEntity locationEntity) {
        return new Location(
                locationEntity.getId(),
                locationEntity.getName(),
                locationEntity.getAddress(),
                locationEntity.getCapacity(),
                locationEntity.getDescription()
        );
    }

    public LocationEntity toEntity(Location location) {
        return new LocationEntity(
                location.id(),
                location.name(),
                location.address(),
                location.capacity(),
                location.description()
        );
    }
}
