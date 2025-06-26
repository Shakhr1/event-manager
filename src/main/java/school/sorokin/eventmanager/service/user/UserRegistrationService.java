package school.sorokin.eventmanager.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sorokin.eventmanager.model.User;
import school.sorokin.eventmanager.model.UserRole;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRegistrationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public User register(User signUpRequest) {
        log.info("Execute method register user: login = {} in UserRegistrationService class", signUpRequest.login());
        if (userService.isUserExistsByLogin(signUpRequest.login())) {
            throw new IllegalArgumentException("User with such login = %s already exist"
                    .formatted(signUpRequest.login()));
        }
        var userToSave = new User(
                null,
                signUpRequest.login(),
                passwordEncoder.encode(signUpRequest.passwordHash()),
                signUpRequest.age(),
                UserRole.USER
        );
        return userService.save(userToSave);
    }
}