package school.sorokin.eventmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import school.sorokin.eventmanager.dto.LocationDto;
import school.sorokin.eventmanager.mapper.LocationDtoMapper;
import school.sorokin.eventmanager.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final static Logger log = LoggerFactory.getLogger(LocationController.class);

    private final LocationDtoMapper locationDtoMapper;
    private final LocationService locationService;

    public LocationController(LocationDtoMapper locationDtoMapper, LocationService locationService) {
        this.locationDtoMapper = locationDtoMapper;
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody @Validated LocationDto locationDto) {
        log.info("Get request for location create: locationDto={}", locationDto);
        var createdLocation = locationService.createLocation(locationDtoMapper.toDomain(locationDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(locationDtoMapper.toDto(createdLocation));
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable("locationId") Long id) {
        log.info("Get request for get location: locationId={}", id);
        var foundLocation = locationService.getLocationById(id);
        return ResponseEntity.ok()
                .body(locationDtoMapper.toDto(foundLocation));
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable("locationId") Long id,
                                                      @RequestBody @Validated LocationDto updateLocationDto
    ) {
        log.info("Get request for update location: locationId={}, updateLocationDto={}", id, updateLocationDto);
        var updatedLocation = locationService.updateLocation(locationDtoMapper.toDomain(updateLocationDto), id);
        return ResponseEntity.ok(locationDtoMapper.toDto(updatedLocation));
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        log.info("Get request for get all locations");
        var locationList = locationService.getAllLocations();
        return ResponseEntity.ok(locationList.stream().map(locationDtoMapper::toDto).toList());
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<Void> deleteLocation(@PathVariable("locationId") Long id) {
        log.info("Delete request for get location: locationId={}", id);
        locationService.deleteLocation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
