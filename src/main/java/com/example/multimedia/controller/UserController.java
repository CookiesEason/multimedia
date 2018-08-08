package com.example.multimedia.controller;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserInfo;
import com.example.multimedia.dto.SimpleUserDTO;
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

    @PostMapping("/api/user/register")
    private ResultVo registerUser(@RequestBody @Validated User user){
        return userService.save(user);
    }

    @GetMapping("/api/user/activateEmail")
    private ResultVo activateEmail(@RequestParam String username,@RequestParam String activeCode){
        return userService.activateEmail(username,activeCode);
    }

    @GetMapping("/api/user/info")
    private ResultVo info(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return ResultVoUtil.success(userService.findByUsername(userDetails.getUsername()));
    }

    @PostMapping("/api/user/info")
    private ResultVo updateInfo(@RequestBody @Validated UserInfo userInfo) {
        return userService.save(userInfo);
    }

    @PostMapping("/api/user/head")
    private ResultVo uploadHead(@RequestParam("file") MultipartFile multipartFile){
        return userService.updateHead(multipartFile);
    }

    @GetMapping("/api/user/{userId}")
    private ResultVo simpleUserInfo(@PathVariable Long userId){
        User user = userService.findById(userId);
        if (user!=null){
            return ResultVoUtil.success(new SimpleUserDTO(user));
        }
        return ResultVoUtil.error(0,"不存在该用户");
    }

}
