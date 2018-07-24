package com.example.multimedia.controller;

import com.example.multimedia.domian.User;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author CookiesEason
 * 2018/07/23 14:59
 */
@RestController
@RequestMapping("user/api/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    private ResultVo registerUser(@RequestBody @Validated User user){
        return userService.save(user);
    }

    @GetMapping("info")
    private ResultVo info(){
        return ResultVoUtil.success(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
