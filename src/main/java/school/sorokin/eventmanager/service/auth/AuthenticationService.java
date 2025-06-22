package school.sorokin.eventmanager.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sorokin.eventmanager.dto.SignInRequest;
import school.sorokin.eventmanager.model.User;
import school.sorokin.eventmanager.security.jwt.JwtTokenManager;
import school.sorokin.eventmanager.service.user.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenManager jwtTokenManager;
    private final UserService userService;

    public String authenticateUser(SignInRequest signInRequest) {
        log.info("Execute method authenticateUser user: login = {} in AuthenticationService class",
                signInRequest.login());

        if (!userService.isUserExistsByLogin(signInRequest.login())) {
            throw new BadCredentialsException("Bad credentials");
        }
        var user = userService.findByLogin(signInRequest.login());
        if (!passwordEncoder.matches(signInRequest.password(), user.passwordHash())) {
            throw new BadCredentialsException("Bad credentials");
        }

        return jwtTokenManager.generateJwtToken(user);
    }

    public User getCurrentAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authentication not present");
        }
        return (User) authentication.getPrincipal();
    }
}
