package com.root34.aurora.survey.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {

    String questionNo;

    String surveyCode;

    String questionType;

    String questionBody;

    List<ChoiceDTO> choices;
}
