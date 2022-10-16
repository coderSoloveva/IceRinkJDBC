package ru.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Time;
import java.time.DayOfWeek;

@Data
public class DayDtoRequest {
    @NotNull
    DayOfWeek name;
    Time start_time;
    Time end_time;
    @NotNull
    Boolean is_holiday;
}
