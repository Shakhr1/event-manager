package school.sorokin.eventmanager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import school.sorokin.eventmanager.dto.EventCreateRequestDto;
import school.sorokin.eventmanager.dto.EventDto;
import school.sorokin.eventmanager.dto.EventSearchRequestDto;
import school.sorokin.eventmanager.dto.EventUpdateRequestDto;
import school.sorokin.eventmanager.mapper.EventDtoMapper;
import school.sorokin.eventmanager.service.event.EventService;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService eventService;
    private final EventDtoMapper eventDtoMapper;

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody @Validated EventCreateRequestDto createRequestDto) {
        log.info("Create event: {}", createRequestDto);
        var createdEvent = eventService.createEvent(createRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventDtoMapper.toDto(createdEvent));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable("eventId") Long eventId,
                                                @RequestBody @Validated EventUpdateRequestDto updateRequestDto) {
        log.info("Update event: {}", updateRequestDto);
        var updatedEvent = eventService.updateEvent(eventId, updateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(eventDtoMapper.toDto(updatedEvent));
    }

    @PostMapping("/search")
    public ResponseEntity<List<EventDto>> searchEvents(@RequestBody @Validated EventSearchRequestDto searchRequest) {
        log.info("Search event: {}", searchRequest);
        var searchEvents = eventService.searchByFilter(searchRequest);
        return ResponseEntity.status(HttpStatus.OK).body(searchEvents.stream().map(eventDtoMapper::toDto).toList());
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> cancelEvent(@PathVariable("eventId") Long eventId) {
        log.info("Cancel event: {}", eventId);
        eventService.cancelEvent(eventId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getById(@PathVariable("eventId") Long eventId) {
        log.info("Get event: {}", eventId);
        var eventDto = eventService.getEventById(eventId);
        return ResponseEntity.status(HttpStatus.OK).body(eventDtoMapper.toDto(eventDto));
    }

    @GetMapping("/my")
    public ResponseEntity<List<EventDto>> getCurrentUserEvents() {
        log.info("Get request for get all user events");
        var events = eventService.getCurrentUserEvents();
        return ResponseEntity.status(HttpStatus.OK).body(events.stream().map(eventDtoMapper::toDto).toList());
    }
}
