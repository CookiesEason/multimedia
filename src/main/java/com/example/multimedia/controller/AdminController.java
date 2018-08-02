package com.example.multimedia.controller;

import com.example.multimedia.service.UserService;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CookiesEason
 * 2018/08/02 19:41
 */
@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("users")
    private ResultVo findUsers(@RequestParam(defaultValue = "0") int page){
        return userService.findALL(page);
    }

}
