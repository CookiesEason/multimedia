package com.example.multimedia.service.impl.mainserviceimpl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.Comment;
import com.example.multimedia.domian.maindomian.Reply;
import com.example.multimedia.domian.maindomian.ReplyLike;
import com.example.multimedia.repository.ReplyLikeRepository;
import com.example.multimedia.service.*;
import com.example.multimedia.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/07 12:56
 */
@Service(value = "ReplyLikeService")
public class ReplyLikeServiceImpl implements LikeService {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ReplyLikeRepository replyLikeRepository;

    @Autowired
    private NoticeService noticeService;

    @Override
    public Long like(Long replyId,Topic topic) {
        if (replyService.findById(replyId)==null){
            return null;
        }
        Long userId = getUid();
        ReplyLike replyLike = status(replyId,userId,null);
        if (replyLike == null){
            replyLike = new ReplyLike();
            replyLike.setStatus(true);
            replyLike.setReplyId(replyId);
            replyLike.setUserId(getUid());
            Comment comment = (Comment) commentService.findById(
                    replyService.findById(replyLike.getReplyId()).getCommentId());
            Reply reply = replyService.findById(replyLike.getReplyId());
            String title;
            if (comment.getTopic().equals(Topic.VIDEO)){
                title = videoService.findById(comment.getTopId()).getTitle();
            }else {
                title = articleService.findById((long)comment.getTopId()).getTitle();
            }
            noticeService.saveNotice(comment.getTopic(), comment.getTopId(), title,comment.getId(),comment.getContent(),
                    reply.getContent(),userId,
                    reply.getFromUid(),"replyPraise");
        }else {
            replyLike.setStatus(!replyLike.isStatus());
        }
        replyLikeRepository.save(replyLike);
        return 0L;
    }

    @Override
    public Long countAllById(Long id) {
        return replyLikeRepository.countAllByReplyIdAndStatus(id,true);
    }

    @Override
    public void deleteAllByIds(List<Long> replyIds) {
        replyLikeRepository.deleteAllByReplyIdIn(replyIds);
    }

    @Override
    public void deleteAllById(Long id) {
        replyLikeRepository.deleteAllByReplyId(id);
    }

    @Override
    public void deleteAllById(Long id, Topic topic) {

    }

    @Override
    public ReplyLike status(Long replyId, Long userId,Topic topic){
        return replyLikeRepository.findByUserIdAndReplyId(userId,replyId);
    }

    private Long getUid(){
        User user = userService.findByUsername(UserUtil.getUserName());
        if (user!=null){
            return user.getId();
        }
        return null;
    }
}
