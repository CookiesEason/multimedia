package com.example.multimedia.handler;

import com.example.multimedia.exception.UserException;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/07/23 15:42
 * 异常处理
 */
@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultVo handleUserException(MethodArgumentNotValidException e){
        List<String> messages = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(objectError ->
                messages.add(objectError.getDefaultMessage()));
        return ResultVoUtil.error(0,messages.toString());
    }

}
