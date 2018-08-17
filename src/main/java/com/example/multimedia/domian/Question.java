package com.example.multimedia.domian;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author CookiesEason
 * 2018/08/16 13:46
 */
@Entity
@Data
public class Question implements Serializable {

    private static final long serialVersionUID = 5486171746287269038L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NotBlank(message = "问题不能为空")
    private String text;

    @NotBlank(message = "A选项不能为空")
    private String optionA;

    @NotBlank(message = "B选项不能为空")
    private String optionB;

    @NotBlank(message = "C选项不能为空")
    private String optionC;

    @NotBlank(message = "D选项不能为空")
    private String optionD;

    @NotNull(message = "答案不能为空")
    private Character answer;

    @JsonIgnore
    public char getAnswer() {
        return answer;
    }

    @JsonProperty
    public void setAnswer(char answer) {
        this.answer = answer;
    }

}
