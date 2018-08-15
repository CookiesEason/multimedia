package com.example.multimedia.service.impl.videoserviceimpl;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.videodomian.VideoComment;
import com.example.multimedia.domian.videodomian.VideoCommentLike;
import com.example.multimedia.repository.VideoCommentLikeRepository;
import com.example.multimedia.service.CommentService;
import com.example.multimedia.service.LikeService;
import com.example.multimedia.service.NoticeService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/07 14:19
 */
@Service(value = "VideoCommentLikeService")
public class VideoCommentLikeServiceImpl implements LikeService {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier(value = "VideoCommentService")
    private CommentService commentService;

    @Autowired
    private VideoCommentLikeRepository videoCommentLikeRepository;

    @Autowired
    private NoticeService noticeService;

    @Override
    public void like(Long commentId) {
        VideoComment videoComment = (VideoComment) commentService.findById(commentId);
        System.out.println(videoComment);
        if (videoComment==null){
            return;
        }
        VideoCommentLike videoCommentLike = status(commentId);
        if (videoCommentLike == null){
            videoCommentLike = new VideoCommentLike();
            videoCommentLike.setStatus(true);
            videoCommentLike.setUserId(getUid());
            videoCommentLike.setCommentId(commentId);
        }else {
            videoCommentLike.setStatus(!videoCommentLike.isStatus());
        }
        videoCommentLikeRepository.save(videoCommentLike);
        if (videoCommentLike.isStatus()){
            noticeService.saveNotice(Topic.VIDEO,videoComment.getVideoId(),commentId,null,getUid()
                    ,videoComment.getFromUid(),"commentPraise");
        }

    }

    @Override
    public Long countAllById(Long id) {
        return videoCommentLikeRepository.countAllBycommentIdAndStatus(id,true);
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
        videoCommentLikeRepository.deleteAllByCommentIdIn(ids);
    }

    @Override
    public void deleteAllById(Long id) {
        videoCommentLikeRepository.deleteAllByCommentId(id);
    }

    @Override
    public VideoCommentLike status(Long commentId){
        return videoCommentLikeRepository.findByUserIdAndCommentId(getUid(),commentId);
    }


    private Long getUid(){
        return userService.findByUsername(UserUtil.getUserName()).getId();
    }

}
