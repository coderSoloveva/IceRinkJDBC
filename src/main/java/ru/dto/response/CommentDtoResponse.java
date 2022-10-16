package ru.dto.response;

import lombok.Data;

@Data
public class CommentDtoResponse {
    Integer id;
    String text;
    Integer ice_rink_id;
    String user_name;
    String time;
}
