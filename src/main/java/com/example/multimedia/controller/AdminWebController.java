package com.example.multimedia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author CookiesEason
 * 2018/09/22 14:22
 */
@Controller
public class AdminWebController {

    @RequestMapping("/admin/login")
    public String adminLoginIndex(){
        return "login";
    }

}
