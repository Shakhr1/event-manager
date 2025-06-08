package school.sorokin.eventmanager.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import school.sorokin.eventmanager.mapper.LocationEntityMapper;
import school.sorokin.eventmanager.model.Location;
import school.sorokin.eventmanager.repository.LocationRepository;

import java.util.List;

@Service
public class LocationService {
    private final LocationEntityMapper entityMapper;
    private final LocationRepository locationRepository;

    public LocationService(LocationEntityMapper entityMapper, LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
        this.entityMapper = new LocationEntityMapper();
    }

    public Location createLocation(Location locationToCreate) {
        if (locationToCreate.id() != null) {
            throw new IllegalArgumentException("Can't create location with provided id. Id must be empty");
        }
        var createdLocation = locationRepository.save(entityMapper.toEntity(locationToCreate));
        return entityMapper.toDomain(createdLocation);
    }

    public void deleteLocation(Long id) {
        var entityToDelete = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location entity wasn't found id=%s"
                        .formatted(id)));
        locationRepository.deleteById(id);
        entityMapper.toDomain(entityToDelete);
    }

    public Location updateLocation(Location locationToUpdate, Long id) {
        if (locationToUpdate.id() != null) {
            throw new IllegalArgumentException("Can't update location with provided id. Id must be empty");
        }

        var entityToUpdate = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location entity wasn't found id=%s"
                        .formatted(id)));
        entityToUpdate.setAddress(locationToUpdate.address());
        entityToUpdate.setName(locationToUpdate.name());
        entityToUpdate.setCapacity(locationToUpdate.capacity());
        entityToUpdate.setDescription(locationToUpdate.description());

        var updatedLocation = locationRepository.save(entityToUpdate);

        return entityMapper.toDomain(updatedLocation);
    }

    public Location getLocationById(Long id) {
        var foundEntity = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location entity wasn't found id=%s"
                        .formatted(id)));
        return entityMapper.toDomain(foundEntity);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(entityMapper::toDomain)
                .toList();
    }
}
