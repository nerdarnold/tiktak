package com.atatestcase.business.service;

import com.atatestcase.business.dto.ExpertiseDto;

public interface ExpertiseService {
    ExpertiseDto getExpertiseByCarId(String carId);
    ExpertiseDto createExpertise(ExpertiseDto expertiseDto);
}