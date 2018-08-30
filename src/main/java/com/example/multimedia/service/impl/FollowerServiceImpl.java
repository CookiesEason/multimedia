package com.example.multimedia.service.impl;

import com.example.multimedia.domian.Follower;
import com.example.multimedia.domian.User;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.dto.SimpleUserDTO;
import com.example.multimedia.repository.FollowerRepository;
import com.example.multimedia.service.FollowerService;
import com.example.multimedia.service.NoticeService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/14 14:29
 */
@Service
public class FollowerServiceImpl implements FollowerService {

    private static final int SIZE = 10;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeService noticeService;

    @Override
    public ResultVo followUser(Long followerId) {
        Long userId = getUid();
        Follower follower = findByUserIdAndFollowerId(userId, followerId);
        if (follower!=null){
            follower.setStatus(!follower.getStatus());
        }else {
            follower = new Follower();
            follower.setUserId(userId);
            follower.setFollowerId(followerId);
            follower.setStatus(true);
        }
        if (follower.getStatus()){
            noticeService.saveNotice(Topic.FOLLOW,null,null,
                    null,null,null,userId,followerId,"follow");
        }
        followerRepository.save(follower);
        return ResultVoUtil.success();
    }

    @Override
    public Boolean checkFollow(Long followerId) {
        Follower follower = findByUserIdAndFollowerId(getUid(), followerId);
        if (follower!=null){
            return follower.getStatus();
        }
        return false;
    }

    @Override
    public ResultVo getFollowers(int page,Long userId) {
        Pageable pageable = PageRequest.of(page,SIZE);
        Page<Follower> followerPage = followerRepository.findAllByUserId(userId,pageable);
        return getUsers(followerPage);
    }

    @Override
    public ResultVo getFans(int page,Long userId) {
        Pageable pageable = PageRequest.of(page,SIZE);
        Page<Follower> followerPage = followerRepository.findAllByFollowerId(userId,pageable);
        return getUsers(followerPage);
    }

    private ResultVo getUsers(Page<Follower> followerPage) {
        List<Long> ids = new ArrayList<>();
        followerPage.getContent().forEach(follower -> ids.add(follower.getFollowerId()));
        List<SimpleUserDTO> userDTOS = new ArrayList<>();
        userService.findAllByIdIn(ids).forEach(user -> {
            SimpleUserDTO simpleUser = new SimpleUserDTO(user.getId(),user.getUserInfo().getNickname(),
                    user.getUserInfo().getHeadImgUrl());
            userDTOS.add(simpleUser);
        });
        PageDTO<SimpleUserDTO> users = new PageDTO<>(userDTOS,followerPage.getTotalElements(),
                (long) followerPage.getTotalPages());
        return ResultVoUtil.success(users);
    }

    private Follower findByUserIdAndFollowerId(Long userId, Long followerId){
        return followerRepository.findByUserIdAndFollowerId(userId, followerId);
    }

    private Long getUid(){
        User user = userService.findByUsername(UserUtil.getUserName());
        if (user!=null){
            return user.getId();
        }
        return null;
    }

}
