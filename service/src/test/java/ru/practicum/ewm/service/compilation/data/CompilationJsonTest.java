package ru.practicum.ewm.service.compilation.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewm.service.category.data.CategoryDto;
import ru.practicum.ewm.service.event.data.event.EventShortDto;
import ru.practicum.ewm.service.user.data.UserShortDto;
import ru.practicum.ewm.service.util.UtilConstants;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CompilationJsonTest {
    private final LocalDateTime testTimestamp = LocalDateTime.now();

    @Autowired
    private JacksonTester<CompilationDto> compilationDtoJacksonTester;

    @Autowired
    private JacksonTester<CompilationNewDto> compilationNewDtoJacksonTester;

    @Autowired
    private JacksonTester<CompilationUpdateRequest> compilationUpdateRequestJacksonTester;

    @Test
    void compilationDtoTest() throws Exception {
        CompilationDto compilationDto = CompilationDto.builder()
                .id(1L)
                .title("TestCompilation")
                .pinned(true)
                .events(getEvents())
                .build();

        JsonContent<CompilationDto> jsonContent = compilationDtoJacksonTester.write(compilationDto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathStringValue("$.title").isEqualTo("TestCompilation");
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.pinned").isEqualTo(true);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.events[0].id").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathStringValue("$.events[0].title").isEqualTo("TestEvent");
        assertThat(jsonContent).extractingJsonPathStringValue("$.events[0].annotation").isEqualTo("TestAnnotation");
        assertThat(jsonContent).extractingJsonPathStringValue("$.events[0].eventDate").isEqualTo(testTimestamp.format(UtilConstants.getDefaultDateTimeFormatter()));
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.events[0].paid").isEqualTo(true);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.events[0].confirmedRequests").isEqualTo(1000);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.events[0].views").isEqualTo(10000);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.events[0].initiator.id").isEqualTo(10);
        assertThat(jsonContent).extractingJsonPathStringValue("$.events[0].initiator.name").isEqualTo("TestUser");

        assertThat(jsonContent).extractingJsonPathNumberValue("$.events[0].category.id").isEqualTo(100);
        assertThat(jsonContent).extractingJsonPathStringValue("$.events[0].category.name").isEqualTo("TestCategory");
    }

    @Test
    void compilationNewDtoTest() throws Exception {
        CompilationNewDto compilationNewDto = CompilationNewDto.builder()
                .title("TestCompilationNew")
                .pinned(true)
                .events(Arrays.asList(1L, 2L, 3L))
                .build();

        JsonContent<CompilationNewDto> jsonContent = compilationNewDtoJacksonTester.write(compilationNewDto);

        assertThat(jsonContent).extractingJsonPathStringValue("$.title").isEqualTo("TestCompilationNew");
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.pinned").isEqualTo(true);
        assertThat(jsonContent).extractingJsonPathArrayValue("$.events").containsExactly(1, 2, 3);
    }

    @Test
    void compilationUpdateRequestTest() throws Exception {
        CompilationUpdateRequest compilationUpdateRequest = CompilationUpdateRequest.builder()
                .title("TestCompilationUpdate")
                .pinned(false)
                .events(Arrays.asList(4L, 5L, 6L))
                .build();

        JsonContent<CompilationUpdateRequest> jsonContent = compilationUpdateRequestJacksonTester.write(compilationUpdateRequest);

        assertThat(jsonContent).extractingJsonPathStringValue("$.title").isEqualTo("TestCompilationUpdate");
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.pinned").isEqualTo(false);
        assertThat(jsonContent).extractingJsonPathArrayValue("$.events").containsExactly(4, 5, 6);
    }

    private List<EventShortDto> getEvents() {
        return Arrays.asList(
                EventShortDto.builder()
                        .id(1L)
                        .initiator(getUserShortDto())
                        .category(getCategoryDto())
                        .title("TestEvent")
                        .annotation("TestAnnotation")
                        .eventDate(testTimestamp)
                        .paid(true)
                        .confirmedRequests(1000L)
                        .views(10000L)
                        .build()
        );
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
}