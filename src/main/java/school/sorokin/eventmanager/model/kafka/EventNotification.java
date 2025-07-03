package school.sorokin.eventmanager.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import school.sorokin.eventmanager.model.event.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EventNotification {
    private Long eventId;
    private Long modifiedById;
    private Long ownerId;
    private List<String> subscribersLogins;

    private FieldChange<String> name;
    private FieldChange<Integer> maxPlaces;
    private FieldChange<LocalDateTime> date;
    private FieldChange<Integer> cost;
    private FieldChange<Integer> duration;
    private FieldChange<Long> locationId;
    private FieldChange<EventStatus> status;

    @Getter
    @AllArgsConstructor
    public static class FieldChange<T> {
        private T oldField;
        private T newField;
    }

}
