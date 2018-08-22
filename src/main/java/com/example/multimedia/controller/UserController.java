package com.example.multimedia.controller;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserInfo;
import com.example.multimedia.dto.SimpleUserDTO;
import com.example.multimedia.service.MailService;
import com.example.multimedia.service.NoticeService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/07/23 14:59
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private NoticeService noticeService;

    @PostMapping("/api/user/register")
    private ResultVo registerUser(@Validated User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<String> errors = new ArrayList<>();
            bindingResult.getAllErrors().forEach(objectError -> {
                errors.add(objectError.getDefaultMessage());
            });
            return ResultVoUtil.error(0,errors);
        }
        String role = "ROLE_PRIMARY_USER";
        return userService.save(user,role);
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

    @GetMapping("/api/user/code")
    private ResultVo getCodeForPassword(){
        return mailService.sendPasswordUpdateEmail();
    }

    @PostMapping("/api/user/password")
    private ResultVo updatePassword(@RequestParam String code,@RequestParam String oldPassword,
                                    @RequestParam String newPassword){
        return userService.updatePassword(code,oldPassword,newPassword);
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

    @GetMapping("/api/user/messages")
    private ResultVo getMessages(@RequestParam(defaultValue = "0") int page){
        return noticeService.getNotices(page);
    }

    @GetMapping("/api/user/messages/count")
    private ResultVo countMessages(){
        return noticeService.unRead();
    }

    @DeleteMapping("/api/user/messages/{messageId}")
    private void deleteMessage(@PathVariable Long messageId){
        noticeService.deleteById(messageId);
    }

}
