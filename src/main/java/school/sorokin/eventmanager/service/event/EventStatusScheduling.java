package school.sorokin.eventmanager.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sorokin.eventmanager.model.EventStatus;
import school.sorokin.eventmanager.repository.EventRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventStatusScheduling {
    private final EventRepository eventRepository;

    @Scheduled(cron = "${event.stats.cron}")
    public void checkEventStatus() {
        log.info("Scheduler started");

        var startedEvents = eventRepository.findStartedEventsWithStatus(EventStatus.WAIT_START);
        startedEvents.forEach(eventId -> eventRepository.changeEventStatus(eventId, EventStatus.STARTED));

        var endedEvents = eventRepository.findEndedEventsWithStatus(EventStatus.STARTED);
        endedEvents.forEach(eventId -> eventRepository.changeEventStatus(eventId, EventStatus.FINISHED));
    }
}
