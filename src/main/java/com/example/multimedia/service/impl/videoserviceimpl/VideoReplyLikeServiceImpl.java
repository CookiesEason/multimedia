package com.example.multimedia.service.impl.videoserviceimpl;

import com.example.multimedia.domian.videodomian.VideoReplyLike;
import com.example.multimedia.repository.VideoReplyLikeRepository;
import com.example.multimedia.service.LikeService;
import com.example.multimedia.service.ReplyService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author CookiesEason
 * 2018/08/07 12:56
 */
@Service(value = "VideoReplyLikeService")
public class VideoReplyLikeServiceImpl implements LikeService {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier(value = "VideoReplyService")
    private ReplyService replyService;

    @Autowired
    private VideoReplyLikeRepository videoReplyLikeRepository;

    @Override
    public void like(Long replyId) {
        if (replyService.findById(replyId)==null){
            return;
        }
        VideoReplyLike videoReplyLike = status(replyId);
        if (videoReplyLike == null){
            videoReplyLike = new VideoReplyLike();
            videoReplyLike.setStatus(true);
            videoReplyLike.setReplyId(replyId);
            videoReplyLike.setUserId(getUid());
        }else {
            videoReplyLike.setStatus(!videoReplyLike.isStatus());
        }
        videoReplyLikeRepository.save(videoReplyLike);
    }

    @Override
    public Long countAllById(Long id) {
        return videoReplyLikeRepository.countAllByReplyIdAndStatus(id,true);
    }

    private VideoReplyLike status(Long replyId){
        return videoReplyLikeRepository.findByUserIdAndReplyId(getUid(),replyId);
    }

    private Long getUid(){
        return userService.findByUsername(UserUtil.getUserName()).getId();
    }
}
