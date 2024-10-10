package com.atatestcase.controller;

import com.atatestcase.business.dto.ExpertiseDto;
import com.atatestcase.business.service.ExpertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expertise")
public class ExpertiseController {

    @Autowired
    private ExpertiseService expertiseService;

    @GetMapping("/{carId}")
    public ResponseEntity<ExpertiseDto> getExpertise(@PathVariable String carId) {
        ExpertiseDto expertise = expertiseService.getExpertiseByCarId(carId);
        return ResponseEntity.ok(expertise);
    }

    @PostMapping("/create")
    public ResponseEntity<ExpertiseDto> createExpertise(@RequestBody ExpertiseDto expertiseDto) {
        ExpertiseDto createdExpertise = expertiseService.createExpertise(expertiseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpertise);
    }
}