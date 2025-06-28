package school.sorokin.eventmanager.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import school.sorokin.eventmanager.model.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EventNotification {
    private Long eventId;
    private Long modifiedById;
    private Long ownerId;
    private List<String> subscribersLogins;

    FieldChange<String> name;
    FieldChange<Integer> maxPlaces;
    FieldChange<LocalDateTime> date;
    FieldChange<Integer> cost;
    FieldChange<Integer> duration;
    FieldChange<Long> locationId;
    FieldChange<EventStatus> status;

    @Getter
    @AllArgsConstructor
    public static class FieldChange<T> {
        private T oldField;
        private T newField;
    }

}
