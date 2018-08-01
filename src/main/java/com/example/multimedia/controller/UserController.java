package com.example.multimedia.controller;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserInfo;
import com.example.multimedia.repository.UserRepository;
import com.example.multimedia.service.FileService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author CookiesEason
 * 2018/07/23 14:59
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    private ResultVo registerUser(@Validated User user){
        return userService.save(user);
    }

    @GetMapping("api/user/info")
    private ResultVo info(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return ResultVoUtil.success(userService.findByUsername(userDetails.getUsername()));
    }

    @PostMapping("api/user/info")
    private ResultVo updateInfo(@Validated UserInfo userInfo,
                                @RequestParam(value = "file",required = false)  MultipartFile multipartFile) {
        return userService.save(userInfo, multipartFile);
    }
}
