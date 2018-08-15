package com.example.multimedia.service.impl.videoserviceimpl;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.videodomian.VideoComment;
import com.example.multimedia.domian.videodomian.VideoReplyLike;
import com.example.multimedia.repository.VideoReplyLikeRepository;
import com.example.multimedia.service.*;
import com.example.multimedia.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/07 12:56
 */
@Service(value = "VideoReplyLikeService")
public class VideoReplyLikeServiceImpl implements LikeService {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier(value = "VideoCommentService")
    private CommentService commentService;

    @Autowired
    @Qualifier(value = "VideoReplyService")
    private ReplyService replyService;

    @Autowired
    private VideoReplyLikeRepository videoReplyLikeRepository;

    @Autowired
    private NoticeService noticeService;

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
        if (videoReplyLike.isStatus()){
            VideoComment videoComment = (VideoComment) commentService.findById(
                    replyService.findById(videoReplyLike.getReplyId()).getCommentId());
            noticeService.saveNotice(Topic.VIDEO,videoComment.getVideoId(),videoComment.getId(),
                    videoReplyLike.getReplyId(),videoReplyLike.getUserId(),
                    replyService.findById(videoReplyLike.getReplyId()).getFromUid(),"replyPraise");
        }
    }

    @Override
    public Long countAllById(Long id) {
        return videoReplyLikeRepository.countAllByReplyIdAndStatus(id,true);
    }

    @Override
    public void deleteAllByIds(List<Long> replyIds) {
        videoReplyLikeRepository.deleteAllByReplyIdIn(replyIds);
    }

    @Override
    public void deleteAllById(Long id) {
        videoReplyLikeRepository.deleteAllByReplyId(id);
    }

    @Override
    public VideoReplyLike status(Long replyId){
        return videoReplyLikeRepository.findByUserIdAndReplyId(getUid(),replyId);
    }

    private Long getUid(){
        return userService.findByUsername(UserUtil.getUserName()).getId();
    }
}
