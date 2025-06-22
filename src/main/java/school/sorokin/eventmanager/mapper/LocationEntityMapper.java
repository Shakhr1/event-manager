package school.sorokin.eventmanager.mapper;

import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;
import school.sorokin.eventmanager.entity.LocationEntity;
import school.sorokin.eventmanager.model.Location;

@Mapper(componentModel = "spring")
@Primary
public interface LocationEntityMapper {
    Location toDomain(LocationEntity locationEntity);

    LocationEntity toEntity(Location location);
}
