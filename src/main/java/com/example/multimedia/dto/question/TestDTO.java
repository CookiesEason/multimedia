package com.example.multimedia.dto.question;

import com.example.multimedia.dto.question.QuestionDTO;
import lombok.Data;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/16 19:11
 */
@Data
public class TestDTO {

    Long totalNum;

    Long correctNum;

    List<QuestionDTO> question;

    public TestDTO(Long totalNum, Long correctNum, List<QuestionDTO> question) {
        this.totalNum = totalNum;
        this.correctNum = correctNum;
        this.question = question;
    }
}
