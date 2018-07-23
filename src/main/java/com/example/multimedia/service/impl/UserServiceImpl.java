package com.example.multimedia.service.impl;

import com.example.multimedia.domian.User;
import com.example.multimedia.repository.UserRepository;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (userRepository.findByUsername(user.getUsername())==null){
            userRepository.save(user);
            return ResultVoUtil.success();
        }
        return ResultVoUtil.error(0,"账号已经存在");
    }
}
