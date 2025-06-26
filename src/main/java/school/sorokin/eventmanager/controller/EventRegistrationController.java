package school.sorokin.eventmanager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sorokin.eventmanager.dto.EventDto;
import school.sorokin.eventmanager.mapper.EventDtoMapper;
import school.sorokin.eventmanager.service.auth.AuthenticationService;
import school.sorokin.eventmanager.service.event.EventRegistrationService;

import java.util.List;

@RestController
@RequestMapping("/events/registrations")
@Slf4j
@RequiredArgsConstructor
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;
    private final EventDtoMapper eventDtoMapper;
    private final AuthenticationService authenticationService;

    @RequestMapping("/{eventId}")
    public ResponseEntity<Void> registerOnEvent(@PathVariable("eventId") Long eventId) {
        log.info("Registering on Event {}", eventId);
        eventRegistrationService.registerUserOnEvent(authenticationService.getCurrentAuthenticatedUser(), eventId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/cancel/{eventId}")
    public ResponseEntity<Void> cancelRegisterOnEvent(@PathVariable("eventId") Long eventId) {
        log.info("Canceling register on Event {}", eventId);
        eventRegistrationService.cancelUserRegistration(authenticationService.getCurrentAuthenticatedUser(), eventId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<EventDto>> getUserRegisteredEvents() {
        log.info("Getting registered events");
        var foundEvents = eventRegistrationService.getUserRegisteredEvents(authenticationService.
                getCurrentAuthenticatedUser().id());
        return ResponseEntity.status(HttpStatus.OK).body(foundEvents.stream().map(eventDtoMapper::toDto).toList());
    }
}
