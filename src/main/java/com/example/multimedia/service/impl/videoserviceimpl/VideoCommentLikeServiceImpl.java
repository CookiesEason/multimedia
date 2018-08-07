package com.example.multimedia.service.impl.videoserviceimpl;

import com.example.multimedia.domian.videodomian.VideoCommentLike;
import com.example.multimedia.repository.VideoCommentLikeRepository;
import com.example.multimedia.service.CommentService;
import com.example.multimedia.service.LikeService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

    @Override
    public void like(Long commentId) {
        if (commentService.findById(commentId)==null){
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
    }

    @Override
    public Long countAllById(Long id) {
        return videoCommentLikeRepository.countAllBycommentIdAndStatus(id,true);
    }

    private VideoCommentLike status(Long commentId){
        return videoCommentLikeRepository.findByUserIdAndCommentId(getUid(),commentId);
    }


    private Long getUid(){
        return userService.findByUsername(UserUtil.getUserName()).getId();
    }

}
