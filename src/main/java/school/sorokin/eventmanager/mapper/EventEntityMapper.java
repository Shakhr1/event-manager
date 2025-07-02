package school.sorokin.eventmanager.mapper;

import org.mapstruct.Mapper;
import school.sorokin.eventmanager.entity.EventEntity;
import school.sorokin.eventmanager.model.event.Event;

@Mapper(componentModel = "spring")
public interface EventEntityMapper {
    Event toDomain(EventEntity entity);
}
