package com.example.multimedia.service;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserInfo;
import com.example.multimedia.dto.AdminUserDTO;
import com.example.multimedia.dto.PageDTO;
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
     * 后台用户注册
     * @param user
     * @return ResultVo
     */
    ResultVo save(User user,String role,Boolean active);

    /**
     * 用户查询
     * @param username
     * @return User
     */
    User findByUsername(String username);

    /**
     * 更新个性签名
     * @param signature
     * @return
     */
    ResultVo signature(String signature);

    ResultVo announcement(String announcement);

    /**
     * 更新用户个人信息
     * @param userInfo
     * @return
     */
    ResultVo save(UserInfo userInfo);

    /**
     * 简单用户信息
     * @return
     */
    ResultVo simpleInfo(Long userId);

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
    PageDTO<AdminUserDTO> findUsers(int page);

    /**
     * 搜索用户
     * @param username
     * @param nickname
     * @return
     */
    PageDTO<AdminUserDTO> findByUsernameOrUserInfoNickname(String searchContent);

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

    Long getUserHot(Long userId);

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
     * 本月上月新注册用户
     * @param day
     * @return
     */
    List<Object> newRegisterCountForDays();

    /**
     * 最新点赞5位作者
     * @param userId
     * @return
     */
    ResultVo likeUsers(Long userId);

    /**
     * 喜欢作品 标签占比
     * @param userId
     * @return
     */
    ResultVo likeWorksProportion(Long userId);

    /**
     * 热门作品 标签占比
     * @param userId
     * @return
     */
    ResultVo hotWorksProportion(Long userId);


    /**
     * userCircle
     * @param page
     * @param size
     * @return
     */
    ResultVo userCircle(int page,int size);


}
