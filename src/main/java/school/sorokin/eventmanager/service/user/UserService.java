package school.sorokin.eventmanager.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import school.sorokin.eventmanager.entity.UserEntity;
import school.sorokin.eventmanager.mapper.UserEntityMapper;
import school.sorokin.eventmanager.model.User;
import school.sorokin.eventmanager.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    public User save(User userToSave) {
        log.info("Execute method save user: login = {} in UserService class", userToSave.login());
        UserEntity entityForSave = userEntityMapper.toEntity(userToSave);
        UserEntity saved = userRepository.save(entityForSave);
        return userEntityMapper.toDomain(saved);
    }

    public User findByLogin(String login) {
        log.info("Execute method getUserByLogin user: login = {} in UserService class", login);
        return userRepository.findByLogin(login)
                .map(userEntityMapper::toDomain)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findById(Long userId) {
        log.info("Execute method getUserById user: id = {} in UserService class", userId);
        return userRepository.findById(userId)
                .map(userEntityMapper::toDomain)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean isUserExistsByLogin(String login) {
        log.info("Execute method userExistByLogin user: login = {} in UserService class", login);
        return userRepository.existsByLogin(login);
    }
}