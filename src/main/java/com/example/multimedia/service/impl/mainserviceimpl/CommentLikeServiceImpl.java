package com.example.multimedia.service.impl.mainserviceimpl;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.Comment;
import com.example.multimedia.domian.maindomian.CommentLike;
import com.example.multimedia.repository.CommentLikeRepository;
import com.example.multimedia.service.CommentService;
import com.example.multimedia.service.LikeService;
import com.example.multimedia.service.NoticeService;
import com.example.multimedia.service.UserService;
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
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private NoticeService noticeService;

    @Override
    public void like(Long commentId,Topic topic) {
        Comment comment = (Comment) commentService.findById(commentId);
        System.out.println(comment);
        if (comment ==null){
            return;
        }
        Long userId = getUid();
        CommentLike commentLike = status(commentId,userId,null);
        if (commentLike == null){
            commentLike = new CommentLike();
            commentLike.setStatus(true);
            commentLike.setUserId(getUid());
            commentLike.setCommentId(commentId);
        }else {
            commentLike.setStatus(!commentLike.isStatus());
        }
        commentLikeRepository.save(commentLike);
        if (commentLike.isStatus()){
            noticeService.saveNotice(Topic.VIDEO, comment.getTopId(),commentId,null,userId
                    , comment.getFromUid(),"commentPraise");
        }

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
        return userService.findByUsername(UserUtil.getUserName()).getId();
    }

}
