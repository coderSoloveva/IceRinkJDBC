package ru.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AreaDtoRequest {
    @NotNull
    @NotEmpty
    String name;
    @NotNull
    Integer city_id;
}
