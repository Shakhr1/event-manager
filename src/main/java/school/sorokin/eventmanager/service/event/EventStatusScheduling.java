package school.sorokin.eventmanager.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sorokin.eventmanager.entity.EventEntity;
import school.sorokin.eventmanager.mapper.EventEntityMapper;
import school.sorokin.eventmanager.model.event.EventStatus;
import school.sorokin.eventmanager.repository.EventRegistrationRepository;
import school.sorokin.eventmanager.repository.EventRepository;
import school.sorokin.eventmanager.service.NotificationService;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventStatusScheduling {
    private final EventRepository eventRepository;
    private final NotificationService notificationService;
    private final EventEntityMapper eventEntityMapper;
    private final EventRegistrationRepository eventRegistrationRepository;

    @Scheduled(cron = "${event.stats.cron}")
    public void checkEventStatus() {
        log.info("Scheduler started");

        var startedEvents = eventRepository.findStartedEventsWithStatus(EventStatus.WAIT_START);
        startedEvents.forEach(event -> notify(event, EventStatus.WAIT_START, EventStatus.STARTED));

        var endedEvents = eventRepository.findEndedEventsWithStatus(EventStatus.STARTED);
        endedEvents.forEach(event -> notify(event, EventStatus.STARTED, EventStatus.FINISHED));
    }


    private void notify(EventEntity event, EventStatus oldStatus, EventStatus newStatus) {

        eventRepository.changeEventStatus(event.getId(), newStatus);

        notificationService.getEventNotificationWithChangedStatus(
                eventEntityMapper.toDomain(event),
                oldStatus,
                newStatus,
                null,
                eventRegistrationRepository.findAllUserLoginByEventRegisterIdQuery(event.getId()));
    }
}
