package com.example.multimedia.controller;

import com.example.multimedia.domian.User;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
        String role = "%USER%";
        return userService.findUsers(page,role);
    }

    @PostMapping("users/{userId}")
    private ResultVo updateUserById(@PathVariable Long userId){
        // TODO: 2018/08/10 暂时不写.
        return ResultVoUtil.success();
    }

    @PostMapping("users/enable/{userId}")
    private ResultVo enableUserByUserId(@PathVariable Long userId){
        return userService.enableUserByUserId(userId);
    }

    @DeleteMapping("users/{userId}")
    private ResultVo deleteUserById(@PathVariable Long userId){
        return userService.deleteByUserId(userId);
    }

    @GetMapping("users/search")
    private ResultVo findUser(@RequestParam(required = false) String username,
                              @RequestParam(required = false) String nickname,
                              @RequestParam(required = false) String email){
        return userService.findByUsernameOrEmailOrUserInfoNickname(username,email,nickname);
    }

    @GetMapping("users/admins")
    private ResultVo getAdmins(@RequestParam(defaultValue = "0") int page){
        String role = "%ADMIN%";
        return userService.findUsers(page,role);
    }

    @GetMapping("users/roles")
    private ResultVo getRoles(){
        return userService.getRoles();
    }

    @PostMapping("users/addAdmin")
    private ResultVo addAdmin(@RequestBody @Validated User user){
        // TODO: 2018/08/11 暂时未定 
        return userService.save(user,user.getRoleList().get(1).getRole());
    }

    @PostMapping("users/changeRole")
    private ResultVo changeRole(@RequestParam Long userId,@RequestParam String role){
        return userService.changeRole(userId,role);
    }

}
