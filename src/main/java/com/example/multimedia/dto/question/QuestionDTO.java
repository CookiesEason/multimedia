package com.example.multimedia.dto.question;

import com.example.multimedia.domian.Question;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/16 19:12
 */
@NoArgsConstructor
@Data
public class QuestionDTO {

    private Long id;
    private String content;
    @JsonProperty(value = "A")
    private String optionA;
    @JsonProperty(value = "B")
    private String optionB;
    @JsonProperty(value = "C")
    private String optionC;
    @JsonProperty(value = "D")
    private String optionD;
    private Character correctAnswer;
    private Character chooseAnswer;
    private Boolean correct;

    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.content = question.getText();
        this.optionA = question.getOptionA();
        this.optionB = question.getOptionB();
        this.optionC = question.getOptionC();
        this.optionD = question.getOptionD();
        this.correctAnswer = question.getAnswer();
    }
}
