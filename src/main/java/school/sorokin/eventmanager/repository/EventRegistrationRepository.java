package school.sorokin.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.*;
import school.sorokin.eventmanager.entity.EventEntity;
import school.sorokin.eventmanager.entity.EventRegistrationEntity;

import java.util.*;

public interface EventRegistrationRepository extends JpaRepository<EventRegistrationEntity, Long> {

    @Query("""
            SELECT reg FROM EventRegistrationEntity reg
            WHERE reg.event.id = :eventId
            AND reg.userId = :userId
            """)
    Optional<EventRegistrationEntity> findRegistration(
            @Param("userId") Long userId,
            @Param("eventId") Long eventId
    );

    @Query("""
            SELECT reg.event FROM EventRegistrationEntity reg
            WHERE reg.userId = :userId
            """)
    List<EventEntity> findRegisteredEvents(@Param("userId") Long userId);
}
