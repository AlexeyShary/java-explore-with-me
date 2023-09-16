package ru.practicum.ewm.service.event.data.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewm.service.category.data.CategoryDto;
import ru.practicum.ewm.service.event.data.location.LocationDto;
import ru.practicum.ewm.service.user.data.UserShortDto;
import ru.practicum.ewm.service.util.UtilConstants;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class EventJsonTest {
    private final LocalDateTime eventDateTimestamp = LocalDateTime.now();
    private final LocalDateTime createdOnTimestamp = LocalDateTime.now().plusDays(1);
    private final LocalDateTime publishedOnTimestamp = LocalDateTime.now().plusDays(2);

    @Autowired
    private JacksonTester<EventFullDto> eventFullDtoJacksonTester;

    @Autowired
    private JacksonTester<EventNewDto> eventNewDtoJacksonTester;

    @Autowired
    private JacksonTester<EventRequestStatusUpdateRequest> eventRequestStatusUpdateRequestJacksonTester;

    @Autowired
    private JacksonTester<EventShortDto> eventShortDtoJacksonTester;

    @Autowired
    private JacksonTester<EventUpdateAdminRequest> eventUpdateAdminRequestJacksonTester;

    @Autowired
    private JacksonTester<EventUpdateUserRequest> eventUpdateUserRequestJacksonTester;

    @Test
    void eventFullDtoTest() throws Exception {
        EventFullDto eventFullDto = EventFullDto.builder()
                .id(1L)
                .initiator(getUserShortDto())
                .category(getCategoryDto())
                .location(getLocationDto())
                .title("TestTitle")
                .annotation("TestAnnotation")
                .description("TestDescr")
                .state(EventState.PENDING)
                .eventDate(eventDateTimestamp)
                .createdOn(createdOnTimestamp)
                .publishedOn(publishedOnTimestamp)
                .participantLimit(15)
                .paid(true)
                .requestModeration(false)
                .confirmedRequests(150L)
                .views(1500L)
                .build();

        JsonContent<EventFullDto> jsonContent = eventFullDtoJacksonTester.write(eventFullDto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.initiator.id").isEqualTo(10);
        assertThat(jsonContent).extractingJsonPathStringValue("$.initiator.name").isEqualTo("TestUser");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.category.id").isEqualTo(100);
        assertThat(jsonContent).extractingJsonPathStringValue("$.category.name").isEqualTo("TestCategory");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.location.lat").isEqualTo(1000.0);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.location.lon").isEqualTo(10000.0);
        assertThat(jsonContent).extractingJsonPathStringValue("$.title").isEqualTo("TestTitle");
        assertThat(jsonContent).extractingJsonPathStringValue("$.annotation").isEqualTo("TestAnnotation");
        assertThat(jsonContent).extractingJsonPathStringValue("$.description").isEqualTo("TestDescr");
        assertThat(jsonContent).extractingJsonPathStringValue("$.state").isEqualTo("PENDING");
        assertThat(jsonContent).extractingJsonPathStringValue("$.eventDate").isEqualTo(eventDateTimestamp.format(UtilConstants.getDefaultDateTimeFormatter()));
        assertThat(jsonContent).extractingJsonPathStringValue("$.createdOn").isEqualTo(createdOnTimestamp.format(UtilConstants.getDefaultDateTimeFormatter()));
        assertThat(jsonContent).extractingJsonPathStringValue("$.publishedOn").isEqualTo(publishedOnTimestamp.format(UtilConstants.getDefaultDateTimeFormatter()));
        assertThat(jsonContent).extractingJsonPathNumberValue("$.participantLimit").isEqualTo(15);
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.paid").isEqualTo(true);
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.requestModeration").isEqualTo(false);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.confirmedRequests").isEqualTo(150);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.views").isEqualTo(1500);
    }

    @Test
    void eventNewDtoTest() throws Exception {
        EventNewDto eventNewDto = EventNewDto.builder()
                .category(1L)
                .location(getLocationDto())
                .title("TestTitle")
                .annotation("TestAnnotation")
                .description("TestDescr")
                .eventTimestamp(eventDateTimestamp)
                .participantLimit(15)
                .paid(true)
                .requestModeration(false)
                .build();

        JsonContent<EventNewDto> jsonContent = eventNewDtoJacksonTester.write(eventNewDto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.category").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.location.lat").isEqualTo(1000.0);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.location.lon").isEqualTo(10000.0);
        assertThat(jsonContent).extractingJsonPathStringValue("$.title").isEqualTo("TestTitle");
        assertThat(jsonContent).extractingJsonPathStringValue("$.annotation").isEqualTo("TestAnnotation");
        assertThat(jsonContent).extractingJsonPathStringValue("$.description").isEqualTo("TestDescr");
        assertThat(jsonContent).extractingJsonPathStringValue("$.eventDate").isEqualTo(eventDateTimestamp.format(UtilConstants.getDefaultDateTimeFormatter()));
        assertThat(jsonContent).extractingJsonPathNumberValue("$.participantLimit").isEqualTo(15);
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.paid").isEqualTo(true);
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.requestModeration").isEqualTo(false);
    }

    @Test
    void eventRequestStatusUpdateRequestTest() throws Exception {
        EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest = EventRequestStatusUpdateRequest.builder()
                .requestIds(Arrays.asList(1L, 2L, 3L))
                .status(EventRequestStatusUpdateRequest.StateAction.CONFIRMED)
                .build();

        JsonContent<EventRequestStatusUpdateRequest> jsonContent = eventRequestStatusUpdateRequestJacksonTester.write(eventRequestStatusUpdateRequest);

        assertThat(jsonContent).extractingJsonPathArrayValue("$.requestIds").containsExactly(1, 2, 3);
        assertThat(jsonContent).extractingJsonPathStringValue("$.status").isEqualTo("CONFIRMED");
    }

    @Test
    void eventShortDtoTest() throws Exception {
        EventShortDto eventShortDto = EventShortDto.builder()
                .id(1L)
                .initiator(getUserShortDto())
                .category(getCategoryDto())
                .title("TestTitle")
                .annotation("TestAnnotation")
                .eventDate(LocalDateTime.now())
                .paid(true)
                .confirmedRequests(1000L)
                .views(10000L)
                .build();

        JsonContent<EventShortDto> jsonContent = eventShortDtoJacksonTester.write(eventShortDto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.initiator.id").isEqualTo(10); // Подставьте актуальное значение
        assertThat(jsonContent).extractingJsonPathStringValue("$.initiator.name").isEqualTo("TestUser");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.category.id").isEqualTo(100); // Подставьте актуальное значение
        assertThat(jsonContent).extractingJsonPathStringValue("$.category.name").isEqualTo("TestCategory");
        assertThat(jsonContent).extractingJsonPathStringValue("$.title").isEqualTo("TestTitle");
        assertThat(jsonContent).extractingJsonPathStringValue("$.annotation").isEqualTo("TestAnnotation");
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.paid").isEqualTo(true);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.confirmedRequests").isEqualTo(1000);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.views").isEqualTo(10000);
    }

    @Test
    void eventUpdateAdminRequestTest() throws Exception {
        EventUpdateAdminRequest eventUpdateAdminRequest = EventUpdateAdminRequest.builder()
                .category(1L)
                .location(getLocationDto())
                .title("TestTitle")
                .annotation("TestAnnotation")
                .description("TestDescr")
                .eventTimestamp(eventDateTimestamp)
                .participantLimit(15)
                .paid(true)
                .requestModeration(false)
                .stateAction(EventUpdateAdminRequest.StateAction.PUBLISH_EVENT)
                .build();

        JsonContent<EventUpdateAdminRequest> jsonContent = eventUpdateAdminRequestJacksonTester.write(eventUpdateAdminRequest);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.category").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.location.lat").isEqualTo(1000.0);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.location.lon").isEqualTo(10000.0);
        assertThat(jsonContent).extractingJsonPathStringValue("$.title").isEqualTo("TestTitle");
        assertThat(jsonContent).extractingJsonPathStringValue("$.annotation").isEqualTo("TestAnnotation");
        assertThat(jsonContent).extractingJsonPathStringValue("$.description").isEqualTo("TestDescr");
        assertThat(jsonContent).extractingJsonPathStringValue("$.eventDate").isEqualTo(eventDateTimestamp.format(UtilConstants.getDefaultDateTimeFormatter()));
        assertThat(jsonContent).extractingJsonPathNumberValue("$.participantLimit").isEqualTo(15);
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.paid").isEqualTo(true);
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.requestModeration").isEqualTo(false);
        assertThat(jsonContent).extractingJsonPathStringValue("$.stateAction").isEqualTo("PUBLISH_EVENT");
    }

    @Test
    void eventUpdateUserRequestTest() throws Exception {
        EventUpdateUserRequest eventUpdateUserRequest = EventUpdateUserRequest.builder()
                .category(1L)
                .location(getLocationDto())
                .title("TestTitle")
                .annotation("TestAnnotation")
                .description("TestDescr")
                .eventTimestamp(eventDateTimestamp)
                .participantLimit(15)
                .paid(true)
                .requestModeration(false)
                .stateAction(EventUpdateUserRequest.StateAction.SEND_TO_REVIEW)
                .build();

        JsonContent<EventUpdateUserRequest> jsonContent = eventUpdateUserRequestJacksonTester.write(eventUpdateUserRequest);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.category").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.location.lat").isEqualTo(1000.0);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.location.lon").isEqualTo(10000.0);
        assertThat(jsonContent).extractingJsonPathStringValue("$.title").isEqualTo("TestTitle");
        assertThat(jsonContent).extractingJsonPathStringValue("$.annotation").isEqualTo("TestAnnotation");
        assertThat(jsonContent).extractingJsonPathStringValue("$.description").isEqualTo("TestDescr");
        assertThat(jsonContent).extractingJsonPathStringValue("$.eventDate").isEqualTo(eventDateTimestamp.format(UtilConstants.getDefaultDateTimeFormatter()));
        assertThat(jsonContent).extractingJsonPathNumberValue("$.participantLimit").isEqualTo(15);
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.paid").isEqualTo(true);
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.requestModeration").isEqualTo(false);
        assertThat(jsonContent).extractingJsonPathStringValue("$.stateAction").isEqualTo("SEND_TO_REVIEW");
    }

    private UserShortDto getUserShortDto() {
        return UserShortDto.builder()
                .id(10L)
                .name("TestUser")
                .build();
    }

    private CategoryDto getCategoryDto() {
        return CategoryDto.builder()
                .id(100L)
                .name("TestCategory")
                .build();
    }

    private LocationDto getLocationDto() {
        return LocationDto.builder()
                .lat(1000f)
                .lon(10000f)
                .build();
    }
}