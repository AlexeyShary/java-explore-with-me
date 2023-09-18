package ru.practicum.ewm.service.user.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class UserJsonTest {
    @Autowired
    private JacksonTester<UserDto> userDtoJacksonTester;

    @Autowired
    private JacksonTester<UserShortDto> userShortDtoJacksonTester;

    @Test
    void userDtoTest() throws Exception {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("TestName")
                .email("TestEmail")
                .build();

        JsonContent<UserDto> jsonContent = userDtoJacksonTester.write(userDto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathStringValue("$.name").isEqualTo("TestName");
        assertThat(jsonContent).extractingJsonPathStringValue("$.email").isEqualTo("TestEmail");
    }

    @Test
    void userShortDtoTest() throws Exception {
        UserShortDto userShortDto = UserShortDto.builder()
                .id(1L)
                .name("TestName")
                .build();

        JsonContent<UserShortDto> jsonContent = userShortDtoJacksonTester.write(userShortDto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathStringValue("$.name").isEqualTo("TestName");
    }
}