package com.atatestcase.business.dto;


import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {

    private String questionText;
    private String answer;
    private List<String> photoUrls;
    private String explanation;
}