package ru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dao.IceRinkDao;
import ru.dto.request.ClosingDateDto;
import ru.dto.request.IceRinkDtoRequest;
import ru.dto.response.IceRinkDtoResponse;

import java.util.List;

@RestController
@RequestMapping("/iceRink")
@RequiredArgsConstructor
public class IceRinkController {

    private final IceRinkDao iceRinkDao;

    @GetMapping()
    public ResponseEntity<List<IceRinkDtoResponse>> getIceRinks() {
        List<IceRinkDtoResponse> iceRinkDtoResponses = iceRinkDao.getIceRinks();
        return ResponseEntity.ok(iceRinkDtoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IceRinkDtoResponse> getIceRinkById(@PathVariable("id") Integer id) {
        IceRinkDtoResponse iceRinkDtoResponse = iceRinkDao.getIceRinkById(id);
        return ResponseEntity.ok(iceRinkDtoResponse);
    }

    @PostMapping()
    public ResponseEntity<IceRinkDtoResponse> createIceRink(@RequestBody IceRinkDtoRequest RequestIceRinkDto) {
        IceRinkDtoResponse iceRinkDtoResponse = iceRinkDao.createIceRink(RequestIceRinkDto);
        return ResponseEntity.ok(iceRinkDtoResponse);
    }

    @PatchMapping()
    public ResponseEntity<Void> addClosingDateIceRink(@RequestBody ClosingDateDto closingDateDto) {
        iceRinkDao.addClosingDateIceRink(closingDateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIceRinkById(@PathVariable("id") Integer id) {
        iceRinkDao.deleteIceRinkById(id);
        return ResponseEntity.ok().build();
    }
}


