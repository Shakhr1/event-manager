package school.sorokin.eventmanager.mapper;

import org.mapstruct.Mapper;
import school.sorokin.eventmanager.dto.LocationDto;
import school.sorokin.eventmanager.model.location.Location;

@Mapper(componentModel = "spring")
public interface LocationDtoMapper {
    Location toDomain(LocationDto locationDto);

    LocationDto toDto(Location location);
}
