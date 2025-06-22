package school.sorokin.eventmanager.mapper;

import org.mapstruct.Mapper;
import school.sorokin.eventmanager.dto.EventDto;
import school.sorokin.eventmanager.model.Event;

@Mapper(componentModel = "spring")
public interface EventDtoMapper {
    EventDto toDto(Event event);
}
