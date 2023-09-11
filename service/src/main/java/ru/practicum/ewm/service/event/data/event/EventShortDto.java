package ru.practicum.ewm.service.event.data.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.service.category.data.CategoryDto;
import ru.practicum.ewm.service.user.data.UserShortDto;

import java.time.LocalDateTime;

@Data
@Builder
public class EventShortDto {
    private Long id;

    private UserShortDto initiator;
    private CategoryDto category;

    private String title;
    private String annotation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Boolean paid;

    private Long confirmedRequests;
    private Long views;
}