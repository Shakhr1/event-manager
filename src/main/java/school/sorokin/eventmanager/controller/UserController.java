package school.sorokin.eventmanager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import school.sorokin.eventmanager.dto.SignInRequest;
import school.sorokin.eventmanager.dto.SignUpRequest;
import school.sorokin.eventmanager.dto.UserDto;
import school.sorokin.eventmanager.mapper.UserDtoMapper;
import school.sorokin.eventmanager.security.jwt.JwtResponse;
import school.sorokin.eventmanager.service.auth.AuthenticationService;
import school.sorokin.eventmanager.service.user.UserRegistrationService;
import school.sorokin.eventmanager.service.user.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final AuthenticationService authenticationService;
    private final UserDtoMapper userDtoMapper;
    private final UserRegistrationService userRegistrationService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        log.info("Post request for SignUp: login = {}", signUpRequest.login());
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
        log.info("Post request for SignIn: login = {}", signInRequest.login());
        return ResponseEntity.ok().body(new JwtResponse(authenticationService.authenticateUser(signInRequest)));
    }
}