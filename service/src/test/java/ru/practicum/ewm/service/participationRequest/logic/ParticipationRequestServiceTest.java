package ru.practicum.ewm.service.participationRequest.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm.service.category.data.Category;
import ru.practicum.ewm.service.event.data.event.Event;
import ru.practicum.ewm.service.event.data.event.EventRepository;
import ru.practicum.ewm.service.event.data.event.EventState;
import ru.practicum.ewm.service.event.data.location.Location;
import ru.practicum.ewm.service.participationRequest.data.ParticipationRequest;
import ru.practicum.ewm.service.participationRequest.data.ParticipationRequestRepository;
import ru.practicum.ewm.service.participationRequest.data.ParticipationRequestState;
import ru.practicum.ewm.service.user.data.User;
import ru.practicum.ewm.service.user.data.UserRepository;
import ru.practicum.ewm.service.util.exception.ConflictException;
import ru.practicum.ewm.service.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipationRequestServiceTest {
    private final LocalDateTime eventDateTimestamp = LocalDateTime.now();
    private final LocalDateTime createdOnTimestamp = LocalDateTime.now().plusDays(1);
    private final LocalDateTime publishedOnTimestamp = LocalDateTime.now().plusDays(2);

    @InjectMocks
    private ParticipationRequestService participationRequestService;

    @Mock
    private ParticipationRequestRepository participationRequestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @Test
    void getAllTest() {
        long userId = 1;

        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(new User()));
        when(participationRequestRepository.findAllByRequesterId(eq(userId))).thenReturn(new ArrayList<>());

        participationRequestService.getAll(userId);

        verify(userRepository, times(1)).findById(eq(userId));
        verify(participationRequestRepository, times(1)).findAllByRequesterId(eq(userId));
        verifyNoMoreInteractions(userRepository, eventRepository, participationRequestRepository);
    }

    @Test
    void create_allValidTest() {
        when(userRepository.findById(eq(2L))).thenReturn(Optional.of(getParticipator()));
        when(eventRepository.findById(eq(1000L))).thenReturn(Optional.of(getEvent()));
        when(participationRequestRepository.countByEventIdAndStatus(eq(1000L), eq(ParticipationRequestState.CONFIRMED))).thenReturn(90L);
        when(participationRequestRepository.save(any(ParticipationRequest.class))).thenReturn(new ParticipationRequest());

        participationRequestService.create(2, 1000);

        verify(userRepository, times(1)).findById(eq(2L));
        verify(eventRepository, times(1)).findById(eq(1000L));
        verify(participationRequestRepository, times(1)).countByEventIdAndStatus(eq(1000L), eq(ParticipationRequestState.CONFIRMED));
        verify(participationRequestRepository, times(1)).save(any(ParticipationRequest.class));
        verifyNoMoreInteractions(userRepository, eventRepository, participationRequestRepository);
    }

    @Test
    void create_byInitiatorTest() {
        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(getInitiator()));
        when(eventRepository.findById(eq(1000L))).thenReturn(Optional.of(getEvent()));

        assertThrows(ConflictException.class, () -> {
            participationRequestService.create(1, 1000);
        });

        verify(userRepository, times(1)).findById(eq(1L));
        verify(eventRepository, times(1)).findById(eq(1000L));
        verifyNoMoreInteractions(userRepository, eventRepository, participationRequestRepository);
    }

    @Test
    void create_toNotPublishedEventTest() {
        Event event = getEvent();
        event.setState(EventState.PENDING);

        when(userRepository.findById(eq(2L))).thenReturn(Optional.of(getParticipator()));
        when(eventRepository.findById(eq(1000L))).thenReturn(Optional.of(event));

        assertThrows(ConflictException.class, () -> {
            participationRequestService.create(2, 1000);
        });

        verify(userRepository, times(1)).findById(eq(2L));
        verify(eventRepository, times(1)).findById(eq(1000L));
        verifyNoMoreInteractions(userRepository, eventRepository, participationRequestRepository);
    }

    @Test
    void create_FullParticipationLimitTest() {
        when(userRepository.findById(eq(2L))).thenReturn(Optional.of(getParticipator()));
        when(eventRepository.findById(eq(1000L))).thenReturn(Optional.of(getEvent()));
        when(participationRequestRepository.countByEventIdAndStatus(eq(1000L), eq(ParticipationRequestState.CONFIRMED))).thenReturn(100L);

        assertThrows(ConflictException.class, () -> {
            participationRequestService.create(2, 1000);
        });

        verify(userRepository, times(1)).findById(eq(2L));
        verify(eventRepository, times(1)).findById(eq(1000L));
        verify(participationRequestRepository, times(1)).countByEventIdAndStatus(eq(1000L), eq(ParticipationRequestState.CONFIRMED));
        verifyNoMoreInteractions(userRepository, eventRepository, participationRequestRepository);
    }

    @Test
    void patch_allValidTest() {
        when(userRepository.findById(eq(2L))).thenReturn(Optional.of(getParticipator()));
        when(participationRequestRepository.findById(eq(10000L))).thenReturn(Optional.of(getParticipationRequest()));
        when(participationRequestRepository.save(any(ParticipationRequest.class))).thenReturn(new ParticipationRequest());

        participationRequestService.patch(2, 10000);

        verify(userRepository, times(1)).findById(eq(2L));
        verify(participationRequestRepository, times(1)).findById(eq(10000L));
        verify(participationRequestRepository, times(1)).save(any(ParticipationRequest.class));
        verifyNoMoreInteractions(userRepository, eventRepository, participationRequestRepository);
    }

    @Test
    void patch_notRequestOwnerTest() {
        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(getInitiator()));
        when(participationRequestRepository.findById(eq(10000L))).thenReturn(Optional.of(getParticipationRequest()));

        assertThrows(NotFoundException.class, () -> {
            participationRequestService.patch(1, 10000);
        });

        verify(userRepository, times(1)).findById(eq(1L));
        verify(participationRequestRepository, times(1)).findById(eq(10000L));
        verifyNoMoreInteractions(userRepository, eventRepository, participationRequestRepository);
    }

    private User getInitiator() {
        User user = new User();
        user.setId(1L);
        user.setName("Initiator");
        user.setEmail("Initiator@mail.com");
        return user;
    }

    private User getParticipator() {
        User user = new User();
        user.setId(2L);
        user.setName("Participator");
        user.setEmail("Participator@mail.com");
        return user;
    }

    private Category getCategory() {
        Category category = new Category();
        category.setId(10L);
        category.setName("TestCategory");
        return category;
    }

    private Location getLocation() {
        Location location = new Location();
        location.setId(100L);
        location.setLat(100.1f);
        location.setLon(100.2f);
        return location;
    }

    private Event getEvent() {
        Event event = new Event();
        event.setId(1000L);
        event.setInitiator(getInitiator());
        event.setCategory(getCategory());
        event.setLocation(getLocation());
        event.setTitle("TestTitle");
        event.setAnnotation("TestAnnotation");
        event.setDescription("TestDescr");
        event.setState(EventState.PUBLISHED);
        event.setEventDate(eventDateTimestamp);
        event.setCreatedOn(createdOnTimestamp);
        event.setPublishedOn(publishedOnTimestamp);
        event.setParticipantLimit(100);
        event.setPaid(true);
        event.setRequestModeration(true);
        return event;
    }

    private ParticipationRequest getParticipationRequest() {
        ParticipationRequest participationRequest = new ParticipationRequest();
        participationRequest.setId(10000L);
        participationRequest.setEvent(getEvent());
        participationRequest.setRequester(getParticipator());
        participationRequest.setCreated(LocalDateTime.now());
        participationRequest.setStatus(ParticipationRequestState.PENDING);
        return participationRequest;
    }
}