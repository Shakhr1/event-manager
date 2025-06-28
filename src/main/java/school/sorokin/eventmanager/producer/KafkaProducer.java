package school.sorokin.eventmanager.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import school.sorokin.eventmanager.model.kafka.EventNotification;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<Long, EventNotification> kafkaTemplate;

    public void sendEvent(EventNotification eventNotification) {
        log.info("Sending event notification: {}", eventNotification);
        var result = kafkaTemplate.send(
                "notification-topic",
                eventNotification.getEventId(),
                eventNotification
        );
        result.thenAccept(sendResult -> log.info("Successful sent"));
    }
}
