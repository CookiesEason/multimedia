package com.example.multimedia.service.impl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserInfo;
import com.example.multimedia.dto.UsersDTO;
import com.example.multimedia.repository.UserRepository;
import com.example.multimedia.repository.UserRoleRepository;
import com.example.multimedia.service.FileService;
import com.example.multimedia.service.MailService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.EmailUtil;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Autowired
    private MailService mailService;

    @Autowired
    private StringRedisTemplate template;

    @Override
    @Cacheable(value = "user", key = "#id")
    public User findById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public ResultVo save(User user) {
        if (findByUsername(user.getUsername())==null){
            if (findByEmail(user)==null){
                UserInfo userInfo = new UserInfo();
                user.setPassword(encryptPassword(user.getPassword()));
                userInfo.setNickname(user.getUsername());
                user.setUserInfo(userInfo);
                String activateCode = EmailUtil.generateActivateCode(user.getUsername());
                user.setActiveCode(activateCode);
                mailService.sendEmail(user.getEmail(),user.getUsername(),activateCode);
                user.setRoleList(Arrays.asList(userRoleRepository.getOne(1L)));
                userRepository.save(user);
                return ResultVoUtil.success();
            }
            return ResultVoUtil.error(0,"邮箱已被注册");
        }
        return ResultVoUtil.error(0,"账号已经存在");
    }

    @Override
    @Cacheable(value = "user", key = "#username")
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public ResultVo save(UserInfo userInfo) {
        // TODO: 2018/07/30 用户个人中心
        User user = findByUsername(UserUtil.getUserName());
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
    @CacheEvict(value = "user", allEntries = true)
    public ResultVo updatePassword(String code,String password,String passwordAgain) {
        User user = userRepository.findByUsername(UserUtil.getUserName());
        if (code.equals(getCode(user.getEmail()))){
            if (password.length()<8){
                return ResultVoUtil.error(0,"密码长度至少为8位");
            }
            if (password.equals(passwordAgain)){
                user.setPassword(encryptPassword(password));
                userRepository.save(user);
                return ResultVoUtil.success();
            }
            return ResultVoUtil.error(0,"两次密码输入不相同");
        }
        return ResultVoUtil.error(0,"验证码不正确");
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public ResultVo updateHead(MultipartFile multipartFile) {
        ResultVo resultVo = fileService.uploadFile(multipartFile);
        if (resultVo.getCode()==1){
            User user = findByUsername(UserUtil.getUserName());
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
                String activateCode = EmailUtil.generateActivateCode(user.getUsername());
                user.setActiveCode(activateCode);
                user.setDate(new Timestamp(System.currentTimeMillis()));
                userRepository.save(user);
                mailService.sendEmail(user.getEmail(),username,activateCode);
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

    @Override
    public ResultVo findALL(int page) {
        int size=10;
        Pageable pageable = PageRequest.of(page,size);
        Page<User> users = userRepository.findAll(pageable);
        UsersDTO usersDTO = new UsersDTO(users.getContent(),(int) users.getTotalElements(),users.getTotalPages());
        return ResultVoUtil.success(usersDTO);
    }

    private String encryptPassword(String password){
       return new BCryptPasswordEncoder().encode(password);
    }

    private String getCode(String email){
        return template.opsForValue().get(email);
    }

    private boolean checkNickName(UserInfo userInfo){
        return findByUserInfoNickname(userInfo.getNickname()) == null;
    }

}
