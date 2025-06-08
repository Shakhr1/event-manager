package school.sorokin.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sorokin.eventmanager.entity.LocationEntity;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
}
