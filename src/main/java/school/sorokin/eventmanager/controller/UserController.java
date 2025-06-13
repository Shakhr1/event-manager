package school.sorokin.eventmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import school.sorokin.eventmanager.dto.SignInRequest;
import school.sorokin.eventmanager.dto.SignUpRequest;
import school.sorokin.eventmanager.dto.UserDto;
import school.sorokin.eventmanager.mapper.UserDtoMapper;
import school.sorokin.eventmanager.security.jwt.JwtResponse;
import school.sorokin.eventmanager.service.AuthenticationService;
import school.sorokin.eventmanager.service.UserRegistrationService;
import school.sorokin.eventmanager.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final AuthenticationService authenticationService;
    private final UserDtoMapper userDtoMapper;
    private final UserRegistrationService userRegistrationService;
    private final UserService userService;

    public UserController(
            AuthenticationService authenticationService,
            UserDtoMapper userDtoMapper,
            UserRegistrationService userRegistrationService,
            UserService userService
    ) {
        this.authenticationService = authenticationService;
        this.userDtoMapper = userDtoMapper;
        this.userRegistrationService = userRegistrationService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> register(@Validated @RequestBody SignUpRequest signUpRequest) {
        LOGGER.info("Post request for SignUp: login = {}", signUpRequest.login());
        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(userDtoMapper.toDto(userRegistrationService.register(userDtoMapper.toDomain(signUpRequest))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@Validated @PathVariable(name = "id") Long id) {
        return ResponseEntity.
                status(HttpStatus.FOUND)
                .body(userDtoMapper.toDto(userService.findById(id)));
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> authentication(@Validated @RequestBody SignInRequest signInRequest) {
        LOGGER.info("Post request for SignIn: login = {}", signInRequest.login());
        return ResponseEntity.ok().body(new JwtResponse(authenticationService.authenticateUser(signInRequest)));
    }
}