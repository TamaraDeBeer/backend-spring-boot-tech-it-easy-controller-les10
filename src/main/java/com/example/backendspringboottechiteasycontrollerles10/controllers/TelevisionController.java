package com.example.backendspringboottechiteasycontrollerles10.controllers;

import com.example.backendspringboottechiteasycontrollerles10.dtos.televisions.TelevisionDto;
import com.example.backendspringboottechiteasycontrollerles10.dtos.televisions.TelevisionInputDto;
import com.example.backendspringboottechiteasycontrollerles10.exceptions.RecordNotFoundException;
import com.example.backendspringboottechiteasycontrollerles10.models.Television;
import com.example.backendspringboottechiteasycontrollerles10.services.TelevisionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class TelevisionController {

    private final TelevisionService televisionService;

    public TelevisionController(TelevisionService televisionService) {

        this.televisionService = televisionService;
    }

    @GetMapping("/televisions")
    public ResponseEntity<List<TelevisionDto>> getAllTelevisions() {
        return ResponseEntity.ok(televisionService.getAllTelevisions());
    }

    @GetMapping("/televisions/{id}")
    public ResponseEntity<TelevisionDto> getOneTelevision(@PathVariable("id") Long id) {
        TelevisionDto televisionDto = televisionService.getTelevisionById(id);
        return ResponseEntity.ok().body(televisionDto);
    }

    @PostMapping("/televisions")
    public ResponseEntity<TelevisionDto> createTelevision(@Valid @RequestBody TelevisionInputDto televisionInputDto) {
        TelevisionDto televisionDto = televisionService.createTelevision(televisionInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + televisionDto.getId()).toUriString());
        return  ResponseEntity.created(uri).body(televisionDto);
    }


    @PutMapping("/televisions/{id}")
    public ResponseEntity<TelevisionDto> updateTelevision(@PathVariable Long id, @RequestBody TelevisionDto updateTelevisionDto) {
        TelevisionDto televisionDto = televisionService.updateTelevision(id, updateTelevisionDto);
        if (televisionDto.id == null) {
            throw new RecordNotFoundException("Er is geen televisie met dit id nummer: " + id);
        } else {
            return ResponseEntity.ok().body(televisionDto);
        }
    }

    @DeleteMapping("/televisions/{id}")
    public ResponseEntity<Television> deleteTelevision(@PathVariable("id") Long id) {
        televisionService.deleteTelevision(id);
        return ResponseEntity.noContent().build();
    }

    // @PatchMapping snap ik niet.
}