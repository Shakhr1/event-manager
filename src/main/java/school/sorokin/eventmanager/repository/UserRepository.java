package school.sorokin.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sorokin.eventmanager.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
    boolean existsByLogin(String login);
}
