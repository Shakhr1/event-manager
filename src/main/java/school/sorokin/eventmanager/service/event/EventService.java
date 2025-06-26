package school.sorokin.eventmanager.service.event;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import school.sorokin.eventmanager.dto.EventCreateRequestDto;
import school.sorokin.eventmanager.dto.EventSearchRequestDto;
import school.sorokin.eventmanager.dto.EventUpdateRequestDto;
import school.sorokin.eventmanager.entity.EventEntity;
import school.sorokin.eventmanager.mapper.EventEntityMapper;
import school.sorokin.eventmanager.model.Event;
import school.sorokin.eventmanager.model.EventStatus;
import school.sorokin.eventmanager.model.UserRole;
import school.sorokin.eventmanager.repository.EventRepository;
import school.sorokin.eventmanager.service.auth.AuthenticationService;
import school.sorokin.eventmanager.service.location.LocationService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventEntityMapper eventEntityMapper;
    private final LocationService locationService;
    private final AuthenticationService authenticationService;

    public Event createEvent(EventCreateRequestDto createRequestDto) {

        var location = locationService.getLocationById(createRequestDto.locationId());
        if (location.capacity() < createRequestDto.maxPlaces()) {
            throw new IllegalArgumentException("Capacity of location is: %s, but maxPlaces is: %s"
                    .formatted(location.capacity(), createRequestDto.maxPlaces()));
        }

        var entity = getEventEntity(createRequestDto);
        entity = eventRepository.save(entity);
        log.info("New event was created: eventId={}", entity.getId());

        return eventEntityMapper.toDomain(entity);
    }

    public Event updateEvent(Long eventId, EventUpdateRequestDto updateRequest) {
        canCurrentUserModifyEvent(eventId);
        var event = eventRepository.findById(eventId).orElseThrow();

        if (!event.getStatus().equals(EventStatus.WAIT_START)) {
            throw new IllegalArgumentException("Cannot modify event in status: %s"
                    .formatted(event.getStatus()));
        }

        if (updateRequest.maxPlaces() != null || updateRequest.locationId() != null) {
            var locationId = Optional.ofNullable(updateRequest.locationId())
                    .orElse(event.getLocationId());
            var maxPlaces = Optional.ofNullable(updateRequest.maxPlaces())
                    .orElse(event.getMaxPlaces());

            var location = locationService.getLocationById(locationId);
            if (location.capacity() < maxPlaces) {
                throw new IllegalArgumentException(
                        "Capacity of location less than maxPlaces: capacity=%s, maxPlaces=%s"
                                .formatted(location.capacity(), maxPlaces)
                );
            }
        }

        Optional.ofNullable(updateRequest.name()).ifPresent(event::setName);
        Optional.ofNullable(updateRequest.maxPlaces()).ifPresent(event::setMaxPlaces);
        Optional.ofNullable(updateRequest.date()).ifPresent(event::setDate);
        Optional.ofNullable(updateRequest.cost()).ifPresent(event::setCost);
        Optional.ofNullable(updateRequest.duration()).ifPresent(event::setDuration);
        Optional.ofNullable(updateRequest.locationId()).ifPresent(event::setLocationId);

        eventRepository.save(event);

        return eventEntityMapper.toDomain(event);
    }

    public Event getEventById(Long eventId) {
        var event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event entity wasn't found by id=%s"
                        .formatted(eventId)));

        return eventEntityMapper.toDomain(event);
    }

    public void cancelEvent(Long eventId) {
        canCurrentUserModifyEvent(eventId);
        var event = getEventById(eventId);

        if (event.status().equals(EventStatus.CANCELLED)) {
            log.info("Event was already cancelled");
            return;
        }
        if (event.status().equals(EventStatus.FINISHED)
            || event.status().equals(EventStatus.STARTED)) {
            throw new IllegalArgumentException("Cannot cancel event with status: status=%s"
                    .formatted(event.status()));
        }

        eventRepository.changeEventStatus(eventId, EventStatus.CANCELLED);
    }

    public List<Event> searchByFilter(EventSearchRequestDto searchRequest) {
        var foundEntities =  eventRepository.searchEvents(
                searchRequest.name(),
                searchRequest.placesMin(),
                searchRequest.placesMax(),
                searchRequest.dateStartAfter(),
                searchRequest.dateStartBefore(),
                searchRequest.costMin(),
                searchRequest.costMax(),
                searchRequest.durationMin(),
                searchRequest.durationMax(),
                searchRequest.locationId(),
                searchRequest.eventStatus()
        );

        return foundEntities.stream()
                .map(eventEntityMapper::toDomain)
                .toList();
    }

    private EventEntity getEventEntity(EventCreateRequestDto createRequestDto) {
        var currentUser = authenticationService.getCurrentAuthenticatedUser();

        return new EventEntity(
                null,
                createRequestDto.name(),
                currentUser.id(),
                createRequestDto.maxPlaces(),
                List.of(),
                createRequestDto.date(),
                createRequestDto.cost(),
                createRequestDto.duration(),
                createRequestDto.locationId(),
                EventStatus.WAIT_START
        );
    }

    public List<Event> getCurrentUserEvents() {
        var currentUser = authenticationService.getCurrentAuthenticatedUser();
        var userEvents = eventRepository.findAllByOwnerId(currentUser.id());

        return userEvents.stream()
                .map(eventEntityMapper::toDomain)
                .toList();
    }

    private void canCurrentUserModifyEvent(Long eventId) {
        var currentUser = authenticationService.getCurrentAuthenticatedUser();
        var event = getEventById(eventId);

        if (!event.ownerId().equals(currentUser.id())
            && !currentUser.role().equals(UserRole.ADMIN)) {
            throw new IllegalArgumentException("This user cannot modify this event");
        }
    }
}
