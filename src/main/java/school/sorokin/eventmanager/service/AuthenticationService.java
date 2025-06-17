package school.sorokin.eventmanager.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import school.sorokin.eventmanager.dto.SignInRequest;
import school.sorokin.eventmanager.model.User;
import school.sorokin.eventmanager.security.jwt.JwtTokenManager;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;
    private final UserService userService;

    public String authenticateUser(SignInRequest signInRequest) {
        LOGGER.info("Execute method authenticateUser user: login = {} in AuthenticationService class",
                signInRequest.login());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.login(),
                signInRequest.password()
        ));
        User foundUser = userService.findByLogin(signInRequest.login());
        return jwtTokenManager.generateJwtToken(foundUser);
    }
}
