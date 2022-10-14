package ru.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;


@Data
public class IceRinkDtoRequest {
    @NotNull
    @NotEmpty(message = "Name should not be empty")
    String name;
    @NotNull
    Integer city_id;
    @NotNull
    Integer area_id;
    @NotNull
    Date opening_date;
    Date closing_date;
    String description;
}
