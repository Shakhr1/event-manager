package school.sorokin.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import school.sorokin.eventmanager.entity.EventEntity;
import school.sorokin.eventmanager.model.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE EventEntity e SET e.status = :status WHERE e.id = :id")
    void changeEventStatus(@Param("id") Long eventId, @Param("status") EventStatus status);

    @Query("""
            SELECT e FROM EventEntity e
            WHERE (:name IS NULL OR e.name LIKE %:name%)
            AND (:placesMin IS NULL OR e.maxPlaces >= :placesMin)
            AND (:placesMax IS NULL OR e.maxPlaces <= :placesMax)
            AND (:dateStartAfter IS NULL OR e.date >= :dateStartAfter)
            AND (:dateStartBefore IS NULL OR e.date <= :dateStartBefore)
            AND (:costMin IS NULL OR e.cost >= :costMin)
            AND (:costMax IS NULL OR e.cost <= :costMax)
            AND (:durationMin IS NULL OR e.duration >= :durationMin)
            AND (:durationMax IS NULL OR e.duration <= :durationMax)
            AND (:locationId IS NULL OR e.locationId = :locationId)
            AND (:eventStatus IS NULL OR e.status = :eventStatus)
            """)
    List<EventEntity> searchEvents(
            @Param("name") String name,
            @Param("placesMin") Integer placesMin,
            @Param("placesMax") Integer placesMax,
            @Param("dateStartAfter") LocalDateTime dateStartAfter,
            @Param("dateStartBefore") LocalDateTime dateStartBefore,
            @Param("costMin") BigDecimal costMin,
            @Param("costMax") BigDecimal costMax,
            @Param("durationMin") Integer durationMin,
            @Param("durationMax") Integer durationMax,
            @Param("locationId") Integer locationId,
            @Param("eventStatus") EventStatus eventStatus
    );

    List<EventEntity> findAllByOwnerId(Long ownerId);

    @Query("""
            SELECT e.id FROM EventEntity e
            WHERE e.date < CURRENT_TIMESTAMP
            AND e.status = :status
            """)
    List<Long> findStartedEventsWithStatus(@Param("status") EventStatus status);

    @Query(value = """
            SELECT e.id FROM EventEntity e
            WHERE e.date + INTERVAL '1 minute' * e.duration < NOW()
            AND e.status = :status
            """, nativeQuery = true)
    List<Long> findEndedEventsWithStatus(@Param("status") EventStatus status);
}
