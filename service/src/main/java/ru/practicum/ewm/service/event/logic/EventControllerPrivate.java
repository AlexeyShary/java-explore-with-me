package ru.practicum.ewm.service.event.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.event.data.event.*;
import ru.practicum.ewm.service.participationRequest.data.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class EventControllerPrivate {
    private final EventService eventService;

    @GetMapping()
    public List<EventShortDto> getAll(@PathVariable long userId,
                                      @Valid @RequestParam(defaultValue = "0") @Min(0) int from,
                                      @Valid @RequestParam(defaultValue = "10") @Min(1) int size) {
        return eventService.getAllByUserId(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getById(@PathVariable long userId,
                                @PathVariable long eventId) {
        return eventService.getByUserIdAndEventId(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByEventId(@PathVariable long userId,
                                                              @PathVariable long eventId) {
        return eventService.getRequestsByEventId(userId, eventId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable long userId,
                               @Valid @RequestBody EventNewDto eventNewDto) {
        return eventService.create(userId, eventNewDto);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEventInfo(@PathVariable long userId,
                                       @PathVariable long eventId,
                                       @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return eventService.patchByUser(userId, eventId, updateEventUserRequest);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult patchEventRequests(@PathVariable long userId,
                                                             @PathVariable long eventId,
                                                             @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return eventService.patchEventRequests(userId, eventId, eventRequestStatusUpdateRequest);
    }
}