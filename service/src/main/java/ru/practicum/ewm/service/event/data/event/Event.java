package ru.practicum.ewm.service.event.data.event;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.service.category.data.Category;
import ru.practicum.ewm.service.event.data.location.Location;
import ru.practicum.ewm.service.user.data.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_initiator_id")
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "event_category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "event_location_id")
    private Location location;

    @Column(name = "event_title")
    private String title;

    @Column(name = "event_annotation")
    private String annotation;

    @Column(name = "event_description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_state")
    private EventState state;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "event_create_date")
    private LocalDateTime createdOn;

    @Column(name = "event_publish_date")
    private LocalDateTime publishedOn;

    @Column(name = "event_participant_limit")
    private Integer participantLimit;

    @Column(name = "event_is_paid")
    private Boolean paid;

    @Column(name = "event_is_request_moderation")
    private Boolean requestModeration;
}