package school.sorokin.eventmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sorokin.eventmanager.model.User;
import school.sorokin.eventmanager.model.UserRole;

@Service
public class UserRegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationService.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User signUpRequest) {
        LOGGER.info("Execute method register user: login = {} in UserRegistrationService class", signUpRequest.login());
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