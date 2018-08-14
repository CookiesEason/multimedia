package com.example.multimedia.service;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserInfo;
import com.example.multimedia.vo.ResultVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/07/23 15:16
 */
public interface UserService {

    User findById(Long id);

    /**
     * 用户注册
     * @param user
     * @return ResultVo
     */
    ResultVo save(User user,String role);

    /**
     * 用户查询
     * @param username
     * @return User
     */
    User findByUsername(String username);

    /**
     * 更新用户个人信息
     * @param userInfo
     * @return
     */
    ResultVo save(UserInfo userInfo);

    /**
     * 用户信息查询
     * @param nickname
     * @return
     */
    User findByUserInfoNickname(String nickname);

    /**
     * 修改密码
     * @param password
     * @param passwordAgain
     * @return
     */
    ResultVo updatePassword(String code,String password,String passwordAgain);

    /**
     * 头像上传
     * @param multipartFile
     * @return
     */
    ResultVo updateHead(MultipartFile multipartFile);

    /**
     * 激活邮箱
     * @param username
     * @param activeCode
     * @return
     */
    ResultVo activateEmail(String username, String activeCode);

    /**
     * 查找邮箱
     * @param user
     * @return
     */
    User findByEmail(User user);

    /**
     * 查询所有用户
     * @return
     */
    ResultVo findUsers(int page,String role);

    /**
     * 搜索用户
     * @param username
     * @param email
     * @param nickname
     * @return
     */
    ResultVo findByUsernameOrEmailOrUserInfoNickname(String username,String email,String nickname);

    /**
     * 禁用,启用用户
     * @param userId
     * @return
     */
    ResultVo enableUserByUserId(Long userId);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    ResultVo deleteByUserId(Long userId);

    /**
     * 获取角色列表
     * @return
     */
    ResultVo getRoles();

    /**
     * 改变角色
     * @param userId
     * @param role
     * @return
     */
    ResultVo changeRole(Long userId,String role);

    List<User> findAllByIdIn(List<Long> ids);
}
