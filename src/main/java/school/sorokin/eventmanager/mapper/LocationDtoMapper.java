package school.sorokin.eventmanager.mapper;

import org.springframework.stereotype.Component;
import school.sorokin.eventmanager.dto.LocationDto;
import school.sorokin.eventmanager.model.Location;

@Component
public class LocationDtoMapper {

    public Location toDomain(LocationDto locationDto) {
        return new Location(
                locationDto.id(),
                locationDto.name(),
                locationDto.address(),
                locationDto.capacity(),
                locationDto.description()
        );
    }

    public LocationDto toDto(Location location) {
        return new LocationDto(
                location.id(),
                location.name(),
                location.address(),
                location.capacity(),
                location.description()
        );
    }
}
