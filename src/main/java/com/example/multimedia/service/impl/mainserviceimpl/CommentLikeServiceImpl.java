package com.example.multimedia.service.impl.mainserviceimpl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.Comment;
import com.example.multimedia.domian.maindomian.CommentLike;
import com.example.multimedia.repository.CommentLikeRepository;
import com.example.multimedia.service.*;
import com.example.multimedia.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/07 14:19
 */
@Service(value = "CommentLikeService")
public class CommentLikeServiceImpl implements LikeService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private NoticeService noticeService;

    @Override
    public Long like(Long commentId,Topic topic) {
        Comment comment = (Comment) commentService.findById(commentId);
        if (comment ==null){
            return null;
        }
        Long userId = getUid();
        CommentLike commentLike = status(commentId,userId,null);
        if (commentLike == null){
            commentLike = new CommentLike();
            commentLike.setStatus(true);
            commentLike.setUserId(getUid());
            commentLike.setCommentId(commentId);
            comment.setLikeCount(comment.getLikeCount()+1);
            String title;
            if (comment.getTopic().equals(Topic.VIDEO)){
                 title = videoService.findById(comment.getTopId()).getTitle();
            }else {
                title = articleService.findById((long)comment.getTopId()).getTitle();
            }
            noticeService.saveNotice(comment.getTopic(), comment.getTopId(),title,commentId,
                    comment.getContent(),null,userId, comment.getFromUid(),"commentPraise");
        }else {
            commentLike.setStatus(!commentLike.isStatus());
            if (commentLike.isStatus()){
                comment.setLikeCount(comment.getLikeCount()+1);
            }else {
                comment.setLikeCount(comment.getLikeCount()-1);
            }
        }
        commentLikeRepository.save(commentLike);
        commentService.save(comment);
        return comment.getLikeCount();
    }


    @Override
    public Long countAllById(Long id) {
        return commentLikeRepository.countAllBycommentIdAndStatus(id,true);
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
        commentLikeRepository.deleteAllByCommentIdIn(ids);
    }

    @Override
    public void deleteAllById(Long id) {
        commentLikeRepository.deleteAllByCommentId(id);
    }

    @Override
    public void deleteAllById(Long id, Topic topic) {

    }

    @Override
    public CommentLike status(Long commentId, Long userId, Topic topic){
        return commentLikeRepository.findByUserIdAndCommentId(userId,commentId);
    }


    private Long getUid(){
        User user = userService.findByUsername(UserUtil.getUserName());
        if (user!=null){
            return user.getId();
        }
        return null;
    }

}
