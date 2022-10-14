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
import ru.dao.CityDao;
import ru.dto.request.CityDtoRequest;
import ru.dto.response.CityDtoResponse;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityDao cityDao;

    @GetMapping
    public ResponseEntity<List<CityDtoResponse>> getCities() throws SQLException {
        List<CityDtoResponse> cityDtoResponses = cityDao.getСities();
        return ResponseEntity.ok(cityDtoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDtoResponse> getAreaById(@PathVariable Integer id) throws SQLException {
        CityDtoResponse cityDtoResponse = cityDao.getCityById(id);
        return ResponseEntity.ok(cityDtoResponse);
    }

    @PostMapping
    public ResponseEntity<CityDtoResponse> createArea(@RequestBody CityDtoRequest cityDtoRequest) throws SQLException {
        CityDtoResponse cityDtoResponse = cityDao.createCity(cityDtoRequest);
        return ResponseEntity.ok(cityDtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArea(@PathVariable Integer id) throws SQLException {
        cityDao.deleteCity(id);
        return ResponseEntity.ok().build();
    }
}
