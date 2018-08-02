package com.example.multimedia.service.impl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserRole;
import com.example.multimedia.exception.UserException;
import com.example.multimedia.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/07/23 15:24
 */
@Service
@Log4j2
public class SecurityUserImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userService.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        if (!user.isActive()){
            throw new UserException("账号未激活，请检查您的邮箱激活",0);
        }
        if (!user.isEnable()){
            throw new UserException("账号不可用，请联系管理员",0);
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        user.getRoleList().forEach(userRole -> simpleGrantedAuthorities
                .add(new SimpleGrantedAuthority(userRole.getRole())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),simpleGrantedAuthorities);
    }
}
