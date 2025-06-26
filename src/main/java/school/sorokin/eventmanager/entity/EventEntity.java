package school.sorokin.eventmanager.entity;

import jakarta.persistence.*;
import lombok.*;
import school.sorokin.eventmanager.model.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "max_places")
    private int maxPlaces;

    @OneToMany(mappedBy = "event")
    private List<EventRegistrationEntity> registrationList;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "cost")
    private int cost;

    @Column(name = "duration")
    private int duration;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "status")
    private EventStatus status;
}
