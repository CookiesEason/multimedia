package com.example.multimedia.handler;

import com.example.multimedia.exception.UserException;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public ResultVo handleAll(MultipartException e){
        return ResultVoUtil.error(0,"发生错误,请检查你的文件,文件大小不能超过100Mb");
    }

    @ExceptionHandler(UserException.class)
    @ResponseBody
    public ResultVo handleUserException(UserException e){
        return ResultVoUtil.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResultVo handleConstraintViolationException(ConstraintViolationException e){
        List<String> messages = new ArrayList<>();
        e.getConstraintViolations().forEach(constraintViolation -> messages.add(constraintViolation.getMessage()));
        return ResultVoUtil.error(0,messages.toString());
    }
}
