package com.example.multimedia.exception;

import lombok.Data;

/**
 * @author CookiesEason
 * 2018/07/23 15:40
 */
@Data
public class UserException extends RuntimeException {
    private Integer code;

    public UserException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
