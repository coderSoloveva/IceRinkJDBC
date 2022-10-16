package ru.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class CommentDtoRequest {
    @NotNull
    @NotEmpty
    String text;
    @NotNull
    Integer ice_rink_id;
    @NotNull
    @NotEmpty
    String user_name;
    @NotNull
    Timestamp time;
}
