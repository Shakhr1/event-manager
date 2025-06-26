package school.sorokin.eventmanager.mapper;

import org.mapstruct.Mapper;
import school.sorokin.eventmanager.entity.LocationEntity;
import school.sorokin.eventmanager.model.Location;

@Mapper(componentModel = "spring")
public interface LocationEntityMapper {
    Location toDomain(LocationEntity locationEntity);

    LocationEntity toEntity(Location location);
}
