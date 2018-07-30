package com.example.multimedia.service.impl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserInfo;
import com.example.multimedia.repository.UserInfoRepository;
import com.example.multimedia.repository.UserRepository;
import com.example.multimedia.repository.UserRoleRepository;
import com.example.multimedia.service.FileService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author CookiesEason
 * 2018/07/23 15:18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private FileService fileService;

    @Override
    public ResultVo save(User user) {
        if (findByUsername(user.getUsername())==null){
            UserInfo userInfo = new UserInfo();
            user.setPassword(encryptPassword(user.getPassword()));
            userInfo.setNickname(user.getUsername());
            userInfoRepository.save(userInfo);
            user.setUserInfo(userInfo);
            user.setRoleList(Arrays.asList(userRoleRepository.getOne(1L)));
            userRepository.save(user);
            return ResultVoUtil.success();
        }
        return ResultVoUtil.error(0,"账号已经存在");
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public ResultVo save(UserInfo userInfo, MultipartFile multipartFile) {
        // TODO: 2018/07/30 用户个人中心
        ResultVo resultVo = fileService.uploadFile(multipartFile);
        if (findByNickname(userInfo.getNickname())==null ||
                findByNickname(userInfo.getNickname()).getNickname().equals(userInfo.getNickname())){
            if (resultVo.getCode() ==1){
                userInfo.setHeadImgUrl(resultVo.getMsg());
                userInfoRepository.save(userInfo);
                return ResultVoUtil.success();
            }
            return ResultVoUtil.error(0,"发生错误，请重新尝试。");
        }
        return ResultVoUtil.error(0,"用户名已经存在");
    }

    @Override
    public UserInfo findByNickname(String nickname) {
        return userInfoRepository.findByNickname(nickname);
    }

    private String encryptPassword(String password){
       return new BCryptPasswordEncoder().encode(password);
    }
}
