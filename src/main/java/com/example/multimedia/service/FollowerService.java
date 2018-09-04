package com.example.multimedia.service;

import com.example.multimedia.vo.ResultVo;

/**
 * @author CookiesEason
 * 2018/08/14 14:29
 */
public interface FollowerService {

    /**
     * 关注用户
     * @param followerId
     * @return
     */
    ResultVo followUser(Long followerId);

    /**
     * 是否关注
     * @param followerId
     * @return
     */
    Boolean checkFollow(Long followerId);

    /**
     * 获取关注列表
     * @return
     */
    ResultVo getFollowers(int page, int size,Long userId);

    /**
     * 获取粉丝列表
     * @return
     */
    ResultVo getFans(int page, Long userId);


    ResultVo fansForDays(Long userId);

}
