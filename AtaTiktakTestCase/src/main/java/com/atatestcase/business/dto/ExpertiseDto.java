package com.atatestcase.business.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExpertiseDto {

    private String carId;
    private List<QuestionDto> questions;
}