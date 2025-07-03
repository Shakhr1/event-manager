package school.sorokin.eventmanager.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sorokin.eventmanager.entity.EventRegistrationEntity;
import school.sorokin.eventmanager.mapper.EventEntityMapper;
import school.sorokin.eventmanager.model.event.Event;
import school.sorokin.eventmanager.model.event.EventStatus;
import school.sorokin.eventmanager.model.user.User;
import school.sorokin.eventmanager.repository.EventRegistrationRepository;
import school.sorokin.eventmanager.repository.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventRegistrationService {

    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRepository eventRepository;
    private final EventService eventService;
    private final EventEntityMapper eventEntityMapper;

    public void registerUserOnEvent(User currentAuthenticatedUser, Long eventId) {
        var event = eventService.getEventById(eventId);

        if (currentAuthenticatedUser.id().equals(event.ownerId())) {
            throw new IllegalArgumentException("Owner cannot register on his event");
        }

        var registration = eventRegistrationRepository.findRegistration(currentAuthenticatedUser.id(), eventId);
        if (registration.isPresent()) {
            throw new IllegalArgumentException("User already registered on event");
        }

        if (!event.status().equals(EventStatus.WAIT_START)) {
            throw new IllegalArgumentException("Cannot register on event with status=%s"
                    .formatted(event.status()));
        }

        eventRegistrationRepository.save(new EventRegistrationEntity(
                        null,
                        currentAuthenticatedUser.id(),
                        eventRepository.findById(eventId).orElseThrow()
                )
        );
    }

    public void cancelUserRegistration(User currentAuthenticatedUser, Long eventId) {
        var event = eventService.getEventById(eventId);

        var registration = eventRegistrationRepository.findRegistration(currentAuthenticatedUser.id(), eventId);
        if (registration.isEmpty()) {
            throw new IllegalArgumentException("User have not registered on event");
        }

        if (!event.status().equals(EventStatus.WAIT_START)) {
            throw new IllegalArgumentException("Cannot cancel registration on event with status=%s"
                    .formatted(event.status()));
        }

        eventRegistrationRepository.delete(registration.orElseThrow());
    }

    public List<Event> getUserRegisteredEvents(Long userId) {
        var foundEvents = eventRegistrationRepository.findRegisteredEvents(userId);

        return foundEvents.stream().map(eventEntityMapper::toDomain).toList();
    }
}
