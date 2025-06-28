package school.sorokin.eventmanager.model.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.sorokin.eventmanager.entity.EventEntity;
import school.sorokin.eventmanager.model.Event;
import school.sorokin.eventmanager.model.EventStatus;
import school.sorokin.eventmanager.producer.KafkaProducer;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaChecker {
    private final KafkaProducer kafkaProducer;

    public void getEventNotificationWithChangedFields(
            EventEntity oldEvent,
            EventEntity newEvent,
            Long modifierId,
            List<String> subscribersLogins) {

        EventNotification eventNotification = new EventNotification();

        if (newEvent.getName() != null && !oldEvent.getName().equals(newEvent.getName()))
            eventNotification.setName(new EventNotification.FieldChange<>(oldEvent.getName(), newEvent.getName()));

        if (newEvent.getMaxPlaces() != 0 && oldEvent.getMaxPlaces() != newEvent.getMaxPlaces())
            eventNotification.setMaxPlaces(new EventNotification.FieldChange<>(oldEvent.getMaxPlaces(), newEvent.getMaxPlaces()));

        if (newEvent.getDate() != null && !oldEvent.getDate().equals(newEvent.getDate()))
            eventNotification.setDate(new EventNotification.FieldChange<>(oldEvent.getDate(), newEvent.getDate()));

        if (newEvent.getCost() != 0 && !(oldEvent.getCost() == newEvent.getCost()))
            eventNotification.setCost(new EventNotification.FieldChange<>(oldEvent.getCost(), newEvent.getCost()));

        if (newEvent.getDuration() != 0 && oldEvent.getDuration() != newEvent.getDuration())
            eventNotification.setDuration(new EventNotification.FieldChange<>(oldEvent.getDuration(), newEvent.getDuration()));

        if (newEvent.getLocationId() != null && !oldEvent.getLocationId().equals(newEvent.getLocationId()))
            eventNotification.setLocationId(new EventNotification.FieldChange<>(oldEvent.getLocationId(), newEvent.getLocationId()));

        if (newEvent.getStatus() != null && !oldEvent.getStatus().equals(newEvent.getStatus()))
            eventNotification.setStatus(new EventNotification.FieldChange<>(oldEvent.getStatus(), newEvent.getStatus()));

        eventNotification.setEventId(oldEvent.getId());
        eventNotification.setModifiedById(modifierId);
        eventNotification.setOwnerId(oldEvent.getOwnerId());
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
