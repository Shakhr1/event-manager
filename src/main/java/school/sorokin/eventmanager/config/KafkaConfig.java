package school.sorokin.eventmanager.config;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import school.sorokin.eventmanager.model.kafka.EventNotification;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaTemplate<Long, EventNotification> kafkaTemplate(KafkaProperties kafkaProperties) {
        var props = kafkaProperties.buildProducerProperties(new DefaultSslBundleRegistry());
        ProducerFactory<Long, EventNotification> producerFactory = new DefaultKafkaProducerFactory<>(props);
        return new KafkaTemplate<>(producerFactory);
    }
}
