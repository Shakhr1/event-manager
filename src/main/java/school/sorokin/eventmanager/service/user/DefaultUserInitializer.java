package school.sorokin.eventmanager.service.user;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import school.sorokin.eventmanager.model.user.User;
import school.sorokin.eventmanager.model.user.UserRole;

@Component
@RequiredArgsConstructor
public class DefaultUserInitializer {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initUsers() {
        createUserIfNotExists("admin", "admin", UserRole.ADMIN);
        createUserIfNotExists("user", "user", UserRole.USER);
    }

    private void createUserIfNotExists(String login, String password, UserRole role) {

        if (userService.isUserExistsByLogin(login)) {
            return;
        }

        var hashedPass = passwordEncoder.encode(password);
        var user = new User(
                null,
                login,
                hashedPass,
                21,
                role
        );
        userService.save(user);
    }
}
