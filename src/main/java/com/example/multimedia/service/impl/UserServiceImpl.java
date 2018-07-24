package com.example.multimedia.service.impl;

import com.example.multimedia.domian.User;
import com.example.multimedia.repository.UserRepository;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author CookiesEason
 * 2018/07/23 15:18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResultVo save(User user) {
        if (findByUsername(user.getUsername())==null){
            user.setPassword(encryptPassword(user.getPassword()));
            userRepository.save(user);
            return ResultVoUtil.success();
        }
        return ResultVoUtil.error(0,"账号已经存在");
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private String encryptPassword(String password){
       return new BCryptPasswordEncoder().encode(password);
    }
}
