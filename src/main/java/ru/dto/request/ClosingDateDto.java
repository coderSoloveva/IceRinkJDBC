package ru.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class ClosingDateDto {
    @NotNull
    Integer id;
    @NotNull
    @NotEmpty
    Date closing_date;
}
