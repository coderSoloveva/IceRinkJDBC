package ru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dao.AreaDao;
import ru.dto.request.AreaDtoRequest;
import ru.dto.response.AreaDtoResponse;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/area")
@RequiredArgsConstructor
public class AreaController {

    private final AreaDao areaDao;

    @GetMapping
    public ResponseEntity<List<AreaDtoResponse>> getAreas() throws SQLException {
        List<AreaDtoResponse> areaDtoResponses = areaDao.getAreas();
        return ResponseEntity.ok(areaDtoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDtoResponse> getAreaById(@PathVariable Integer id) throws SQLException {
        AreaDtoResponse areaDtoResponse = areaDao.getAreaById(id);
        return ResponseEntity.ok(areaDtoResponse);
    }

    @PostMapping
    public ResponseEntity<AreaDtoResponse> createArea(@RequestBody AreaDtoRequest areaDtoRequest) throws SQLException {
        AreaDtoResponse areaDtoResponses = areaDao.createArea(areaDtoRequest);
        return ResponseEntity.ok(areaDtoResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArea(@PathVariable Integer id) throws SQLException {
        areaDao.deleteArea(id);
        return ResponseEntity.ok().build();
    }
}
