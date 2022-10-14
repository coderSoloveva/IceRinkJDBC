package ru.dto.response;

import lombok.Data;

@Data
public class IceRinkDtoResponse {
    Integer id;
    String name;
    Integer city_id;
    Integer area_id;
    String opening_date;
    String closing_date;
    String description;
}
