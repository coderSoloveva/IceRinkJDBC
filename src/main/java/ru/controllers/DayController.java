package ru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dao.DayDao;
import ru.dto.request.CommentDtoRequest;
import ru.dto.request.DayDtoRequest;
import ru.dto.response.CommentDtoResponse;
import ru.dto.response.DayDtoResponse;

import java.sql.SQLException;
import java.util.List;

@Component
@RequestMapping("/day")
@RequiredArgsConstructor
public class DayController {
    private final DayDao dayDao;

    @GetMapping
    public ResponseEntity<List<DayDtoResponse>> getDay() throws SQLException {
        List<DayDtoResponse> days = dayDao.getDays();
        return ResponseEntity.ok(days);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DayDtoResponse> getDayById(@PathVariable Integer id) throws SQLException {
        DayDtoResponse day = dayDao.getDayById(id);
        return ResponseEntity.ok(day);
    }

    @PostMapping
    public ResponseEntity<DayDtoResponse> addDays(@RequestBody DayDtoRequest dayDtoRequest) throws SQLException {
        DayDtoResponse day = dayDao.addDay(dayDtoRequest);
        return ResponseEntity.ok(day);
    }

}
