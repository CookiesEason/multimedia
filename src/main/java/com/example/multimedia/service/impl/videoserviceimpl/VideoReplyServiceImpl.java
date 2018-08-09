package com.example.multimedia.service.impl.videoserviceimpl;

import com.example.multimedia.domian.videodomian.VideoReply;
import com.example.multimedia.repository.VideoReplyRepository;
import com.example.multimedia.service.CommentService;
import com.example.multimedia.service.LikeService;
import com.example.multimedia.service.ReplyService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author CookiesEason
 * 2018/08/05 20:03
 */
@Service(value ="VideoReplyService")
@Transactional(rollbackFor = Exception.class)
public class VideoReplyServiceImpl implements ReplyService {

    @Autowired
    private VideoReplyRepository videoReplyRepository;

    @Autowired
    @Qualifier(value = "VideoCommentService")
    private CommentService commentService;

    @Autowired
    @Qualifier(value = "VideoReplyLikeService")
    private LikeService videoReplyLikeService;

    @Autowired
    private UserService userService;

    @Override
    public VideoReply findById(Long id) {
        Optional<VideoReply> videoReply = videoReplyRepository.findById(id);
        return videoReply.orElse(null);
    }

    @Override
    public ResultVo reply(Long commentId, String content, Long toUid) {
        VideoReply videoReply = new VideoReply();
        if (commentService.findById(commentId)==null){
            return ResultVoUtil.error(0,"评论不存在,无法进行回复");
        }
        videoReply.setCommentId(commentId);
        videoReply.setFromUid(getUid());
        videoReply.setToUid(toUid);
        videoReply.setContent(content);
        videoReplyRepository.save(videoReply);
        return ResultVoUtil.success();
    }

    @Override
    public List<VideoReply> findAllByCommentId(Long id) {
        return videoReplyRepository.findAllByCommentId(id);
    }

    @Override
    public void deleteById(Long id) {
        videoReplyRepository.deleteByIdAndFromUid(id,getUid());
        videoReplyLikeService.deleteAllById(id);
    }

    @Override
    public void deleteAllByCommentId(Long commentId) {
        List<VideoReply> videoReplies = videoReplyRepository.deleteAllByCommentId(commentId);
        List<Long> ids = new ArrayList<>();
        for (VideoReply videoReply : videoReplies){
            Long id = videoReply.getId();
            ids.add(id);
        }
        videoReplyLikeService.deleteAllByIds(ids);
    }

    @Override
    public void deleteAllByCommentIdIn(List<Long> ids) {
        List<VideoReply> videoReplies = videoReplyRepository.deleteAllByCommentIdIn(ids);
        List<Long> replyIds = new ArrayList<>();
        for (VideoReply videoReply : videoReplies){
            Long id = videoReply.getId();
            replyIds.add(id);
        }
        videoReplyLikeService.deleteAllByIds(replyIds);
    }

    private Long getUid(){
        return userService.findByUsername(UserUtil.getUserName()).getId();
    }

}
