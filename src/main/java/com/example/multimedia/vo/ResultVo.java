package com.example.multimedia.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

/**
 * @author CookiesEason
 * 2018/07/23 15:53
 */
@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = 430185373222528745L;

    /**错误码 */
    private Integer code;

    /**提示信息*/
    private String msg;

    /**具体内容*/
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private T data;

}
