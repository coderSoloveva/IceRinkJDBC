package ru.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CityDtoResponse {
    @NotNull
    Integer id;
    @NotNull
    @NotEmpty(message = "Name should not be empty")
    String name;
}