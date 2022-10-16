package ru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dao.CommentDao;
import ru.dto.request.CommentDtoRequest;
import ru.dto.response.CommentDtoResponse;

import java.sql.SQLException;
import java.util.List;

@Component
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentDao commentDao;

    @GetMapping
    public ResponseEntity<List<CommentDtoResponse>> getComments() throws SQLException {
        List<CommentDtoResponse> comments = commentDao.getComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDtoResponse> getCommentById(@PathVariable Integer id) throws SQLException {
        CommentDtoResponse comment = commentDao.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<CommentDtoResponse> addComments(@RequestBody CommentDtoRequest commentDtoRequest) throws SQLException {
        CommentDtoResponse comment = commentDao.addComment(commentDtoRequest);
        return ResponseEntity.ok(comment);
    }

}
