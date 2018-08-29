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
     * 查询所有用户
     * @return
     */
    ResultVo findUsers(int page,String role);

    /**
     * 搜索用户
     * @param username
     * @param nickname
     * @return
     */
    ResultVo findByUsernameOrUserInfoNickname(String username, String nickname);

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

    /**
     * 根据ids获取批量用户
     * @param ids
     * @return
     */
    List<User> findAllByIdIn(List<Long> ids);

    int getUserHot(Long userId);

    /**
     * 热度
     * @param page
     * @param size
     * @return
     */
    ResultVo findHotUsers(int page, int size);

    /**
     * 确认是否具有评论权限
     * @return
     */
    ResultVo checkAccessComment();

    /**
     * 某时间段新注册用户
     * @param day
     * @return
     */
    int newRegisterCountForDays(int day);
}
