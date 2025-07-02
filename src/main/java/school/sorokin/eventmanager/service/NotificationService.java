package school.sorokin.eventmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import school.sorokin.eventmanager.entity.EventEntity;
import school.sorokin.eventmanager.model.event.Event;
import school.sorokin.eventmanager.model.event.EventStatus;
import school.sorokin.eventmanager.model.kafka.EventNotification;
import school.sorokin.eventmanager.producer.KafkaProducer;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final KafkaProducer kafkaProducer;

    public void getEventNotificationWithChangedFields(
            Event oldEvent,
            Event newEvent,
            Long modifierId,
            List<String> subscribersLogins) {

        EventNotification eventNotification = new EventNotification();
        boolean hasChanges = false;

        if (newEvent.name() != null && !oldEvent.name().equals(newEvent.name())) {
            eventNotification.setName(new EventNotification.FieldChange<>(oldEvent.name(), newEvent.name()));
            hasChanges = true;
        }

        if (newEvent.maxPlaces() != 0 && oldEvent.maxPlaces() != newEvent.maxPlaces()) {
            eventNotification.setMaxPlaces(new EventNotification.FieldChange<>(oldEvent.maxPlaces(), newEvent.maxPlaces()));
            hasChanges = true;
        }

        if (newEvent.date() != null && !oldEvent.date().equals(newEvent.date())) {
            eventNotification.setDate(new EventNotification.FieldChange<>(oldEvent.date(), newEvent.date()));
            hasChanges = true;
        }

        if (newEvent.cost() != 0 && oldEvent.cost() != newEvent.cost()) {
            eventNotification.setCost(new EventNotification.FieldChange<>(oldEvent.cost(), newEvent.cost()));
            hasChanges = true;
        }

        if (newEvent.duration() != 0 && oldEvent.duration() != newEvent.duration()) {
            eventNotification.setDuration(new EventNotification.FieldChange<>(oldEvent.duration(), newEvent.duration()));
            hasChanges = true;
        }

        if (newEvent.locationId() != null && !oldEvent.locationId().equals(newEvent.locationId())) {
            eventNotification.setLocationId(new EventNotification.FieldChange<>(oldEvent.locationId(), newEvent.locationId()));
            hasChanges = true;
        }

        if (newEvent.status() != null && !oldEvent.status().equals(newEvent.status())) {
            eventNotification.setStatus(new EventNotification.FieldChange<>(oldEvent.status(), newEvent.status()));
            hasChanges = true;
        }

        if (!hasChanges) {
            log.info("No changes detected");
            return;
        }

        eventNotification.setEventId(oldEvent.id());
        eventNotification.setModifiedById(modifierId);
        eventNotification.setOwnerId(oldEvent.ownerId());
        eventNotification.setSubscribersLogins(subscribersLogins);

        kafkaProducer.sendEvent(eventNotification);
    }


    public void getEventNotificationWithChangedStatus(
            Event event,
            EventStatus oldStatus,
            EventStatus newStatus,
            Long modifierId,
            List<String> subscribersLogins) {

        EventNotification eventNotification = new EventNotification();

        eventNotification.setStatus(new EventNotification.FieldChange<>(oldStatus, newStatus));

        eventNotification.setEventId(event.id());
        eventNotification.setModifiedById(modifierId);
        eventNotification.setOwnerId(event.ownerId());
        eventNotification.setSubscribersLogins(subscribersLogins);

        kafkaProducer.sendEvent(eventNotification);
    }
}
