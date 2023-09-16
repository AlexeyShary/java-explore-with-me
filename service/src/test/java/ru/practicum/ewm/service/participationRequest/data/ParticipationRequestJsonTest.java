package ru.practicum.ewm.service.participationRequest.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ParticipationRequestJsonTest {
    @Autowired
    private JacksonTester<ParticipationRequestDto> participationRequestDtoJacksonTester;

    @Test
    void participationRequestDtoTest() throws Exception {
        ParticipationRequestDto participationRequestDto = ParticipationRequestDto.builder()
                .id(1L)
                .requester(10L)
                .event(100L)
                .status(ParticipationRequestState.CONFIRMED)
                .build();

        JsonContent<ParticipationRequestDto> jsonContent = participationRequestDtoJacksonTester.write(participationRequestDto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.requester").isEqualTo(10);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.event").isEqualTo(100);
        assertThat(jsonContent).extractingJsonPathStringValue("$.status").isEqualTo("CONFIRMED");
    }
}