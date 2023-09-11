package ru.practicum.ewm.service.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class UtilConstants {
    public LocalDateTime getMaxDateTime() {
        return LocalDateTime.parse("9999-12-31T23:59:59.999999");
    }
}