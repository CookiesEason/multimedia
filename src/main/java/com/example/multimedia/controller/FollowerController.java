package com.example.multimedia.controller;

import com.example.multimedia.service.FollowerService;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CookiesEason
 * 2018/08/14 14:41
 */
@RestController
@RequestMapping("/api/follower")
public class FollowerController {

    @Autowired
    private FollowerService followerService;

    @PostMapping("/{followerId}")
    private ResultVo follower(@PathVariable Long followerId){
        return followerService.followUser(followerId);
    }

    @GetMapping("/checkFollower/{followerId}")
    private Boolean checkFollower(@PathVariable Long followerId){
        return followerService.checkFollow(followerId);
    }

    @GetMapping("/followers/{userId}")
    private ResultVo getFollowers(@PathVariable Long userId,@RequestParam(defaultValue = "0")int page){
        return followerService.getFollowers(page,userId);
    }

    @GetMapping("/fans/{userId}")
    private ResultVo getFans(@PathVariable Long userId,@RequestParam(defaultValue = "0") int page){
        return followerService.getFans(page,userId);
    }


}
