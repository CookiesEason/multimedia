package com.example.multimedia.service.impl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserInfo;
import com.example.multimedia.repository.UserRepository;
import com.example.multimedia.repository.UserRoleRepository;
import com.example.multimedia.service.FileService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.EmailUtil;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

/**
 * @author CookiesEason
 * 2018/07/23 15:18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private FileService fileService;

    @Override
    public ResultVo save(User user) {
        if (findByUsername(user.getUsername())==null){
            if (findByEmail(user)==null){
                UserInfo userInfo = new UserInfo();
                user.setPassword(encryptPassword(user.getPassword()));
                userInfo.setNickname(user.getUsername());
                user.setUserInfo(userInfo);
                user.setActiveCode(EmailUtil.generateActivateCode(user.getEmail()));
                user.setRoleList(Arrays.asList(userRoleRepository.getOne(1L)));
                userRepository.save(user);
                return ResultVoUtil.success();
            }
            return ResultVoUtil.error(0,"邮箱已被注册");
        }
        return ResultVoUtil.error(0,"账号已经存在");
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public ResultVo save(UserInfo userInfo) {
        // TODO: 2018/07/30 用户个人中心
        User user = getUser();
        String originalName = user.getUserInfo().getNickname();
        if (checkNickName(userInfo) || userInfo.getNickname().equals(originalName)) {
            user.setUserInfo(userInfo);
            userRepository.save(user);
            return ResultVoUtil.success();
        }
        return ResultVoUtil.error(0, "用户名已经存在");
    }

    @Override
    public User findByUserInfoNickname(String nickname) {
        return userRepository.findByUserInfoNickname(nickname);
    }

    @Override
    public ResultVo updateHead(MultipartFile multipartFile) {
        ResultVo resultVo = fileService.uploadFile(multipartFile);
        if (resultVo.getCode()==1){
            User user = getUser();
            user.getUserInfo().setHeadImgUrl(resultVo.getData().toString());
            userRepository.save(user);
        }
        return resultVo;
    }

    @Override
    public ResultVo activateEmail(String username, String activeCode) {
        User user = userRepository.findByUsername(username);
        if (user!=null){
            if (user.isActive()){
                return ResultVoUtil.error(1,"已激活成功");
            }
            if ((System.currentTimeMillis()-user.getDate().getTime())>EmailUtil.DAY){
                user.setActiveCode(EmailUtil.generateActivateCode(username));
                user.setDate(new Timestamp(System.currentTimeMillis()));
                userRepository.save(user);
                return ResultVoUtil.error(0,"激活邮件已经过期,稍后重新发送新的激活邮件");
            }else if (user.getActiveCode().equals(activeCode)){
                user.setActive(true);
                userRepository.save(user);
                return ResultVoUtil.success();
            }
            return ResultVoUtil.error(0,"激活失败,请确认邮箱信息");
        }
        return ResultVoUtil.error(0,"激活失败,请确认邮箱信息");
    }

    @Override
    public User findByEmail(User user) {
        return userRepository.findByEmail(user.getEmail());
    }

    private String encryptPassword(String password){
       return new BCryptPasswordEncoder().encode(password);
    }

    private boolean checkNickName(UserInfo userInfo){
        return findByUserInfoNickname(userInfo.getNickname()) == null;
    }

    private User getUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }
}
