package ru.practicum.ewm.service.compilation.data;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.service.event.data.event.Event;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    private Long id;

    @Column(name = "compilation_title")
    private String title;

    @Column(name = "compilation_is_pinned")
    private Boolean pinned;

    @ManyToMany
    @JoinTable(
            name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;
}