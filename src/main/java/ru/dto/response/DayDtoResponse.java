package ru.dto.response;

import lombok.Data;

@Data
public class DayDtoResponse {
    Integer id;
    String name;
    String start_time;
    String end_time;
    Boolean is_holiday;
}
