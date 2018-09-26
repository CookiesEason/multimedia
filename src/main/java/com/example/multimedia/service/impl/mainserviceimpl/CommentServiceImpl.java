package com.example.multimedia.service.impl.mainserviceimpl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.*;
import com.example.multimedia.domian.abstractdomian.AbstractComment;
import com.example.multimedia.dto.CommentDTO;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.dto.ReplyDTO;
import com.example.multimedia.dto.SimpleUserDTO;
import com.example.multimedia.repository.CommentRepository;
import com.example.multimedia.service.*;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/05 19:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    @Qualifier(value = "CommentLikeService")
    private LikeService commentLikeService;

    @Autowired
    @Qualifier(value = "ReplyLikeService")
    private LikeService replyLikeService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserService userService;

    @Override
    public ResultVo createComment(Long topicId,String content,Topic topic) {
        Comment comment = new Comment();
        if (videoService.findById(topicId)==null&&topic.equals(Topic.VIDEO)){
            return ResultVoUtil.error(0,"视频不存在,无法进行评论");
        }
        if (articleService.findById((long)topicId)==null&&topic.equals(Topic.ARTICLE)){
            return ResultVoUtil.error(0,"文章不存在,无法进行评论");
        }
        Long fromUid = userService.findByUsername(UserUtil.getUserName()).getId();
        comment.setTopId(topicId);
        comment.setContent(content);
        comment.setFromUid(fromUid);
        comment.setTopic(topic);
        Comment rs = commentRepository.save(comment);
        CommentDTO commentDTO = new CommentDTO(rs,userService.findById(fromUid));
        Long toUid;
        String title;
        if (topic.equals(Topic.VIDEO)){
            Video video = videoService.findById(topicId);
            video.setCommentNum(video.getCommentNum()+1);
            videoService.save(video);
            toUid = video.getUserId();
            title = video.getTitle();
        }else {
            Article article = articleService.findById((long)topicId);
            article.setCommentNum(article.getCommentNum()+1);
            articleService.saveArticle(article);
            toUid = article.getUserId();
            title = article.getTitle();
        }
        if (!fromUid.equals(toUid)){
            noticeService.saveNotice(topic,topicId, title,comment.getId(),comment.getContent(),
                    null,fromUid,toUid,"comment");
        }
        return ResultVoUtil.success(commentDTO);
    }

    @Override
    public ResultVo save(Comment comment) {
        commentRepository.save(comment);
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo getComments(Long topId,Topic topic,int commentPage,String sort) {
        int size=10;
        Pageable pageable = PageRequest.of(commentPage,size,sort("DESC",sort));
        Page<Comment> c = commentRepository.findAllByTopIdAndTopic(pageable,topId,topic);
        List<CommentDTO> commentList = new ArrayList<>();
        c.getContent().forEach(comment -> commentResultVO(commentList, comment));
        PageDTO<CommentDTO> comments = new PageDTO<>(commentList,c.getTotalElements(),
                (long) c.getTotalPages());
        return ResultVoUtil.success(comments);
    }

    @Override
    public AbstractComment findById(Long id) {
        return commentRepository.findCommentById(id);
    }

    @Override
    public void deleteById(Long id) {
        Long deleteId = commentRepository.deleteByIdAndFromUid(id,getUid());
        searchService.deleteCommentById(id);
        if (deleteId!=0){
            replyService.deleteAllByCommentId(id);
        }
        commentLikeService.deleteAllById(id);
    }

    @Override
    public void deleteAllBycontentId(Long topId,Topic topic) {
        searchService.deleteAllByTopicId(topId,topic);
        List<Comment> comments = commentRepository.deleteAllByTopIdAndTopic(topId,topic);
        List<Long> ids= new ArrayList<>();
        for (Comment comment : comments){
           Long id =  comment.getId();
           ids.add(id);
        }
        replyService.deleteAllByCommentIdIn(ids);
        commentLikeService.deleteAllByIds(ids);
    }

    @Override
    public ResultVo findAll(int page, int size, String order,String sort) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<Comment> commentPage = commentRepository.findAll(pageable);
        List<CommentDTO> commentList = new ArrayList<>();
        commentPage.getContent().forEach(comment -> {
           User user = userService.findById(comment.getFromUid());
           CommentDTO commentDTO = new CommentDTO(comment, user);
           commentList.add(commentDTO);
       });
        PageDTO<CommentDTO> comments = new PageDTO<>(commentList,commentPage.getTotalElements(),
                (long) commentPage.getTotalPages());
        return ResultVoUtil.success(comments);
    }

    @Override
    public ResultVo findMyself(Long contentId,Topic topic) {
        Long userId = getUid();
        if (userId==null){
            return ResultVoUtil.error(0,"未登录");
        }
        List<Comment> c = commentRepository.findAllByTopIdAndTopicAndFromUid(contentId,topic,userId);
        List<CommentDTO> commentList = new ArrayList<>();
        c.forEach(comment -> commentResultVO(commentList, comment));
        return ResultVoUtil.success(commentList);
    }

    @Override
    public Long num(Long id, Topic topic) {
        return commentRepository.countAllByTopicAndTopId(topic, id);
    }

    private void commentResultVO(List<CommentDTO> commentList, Comment comment) {
        boolean isLike = false;
        CommentLike commentLike = (CommentLike) commentLikeService.status(comment.getId(),getUid(),null);
        if (commentLike!=null&&commentLike.isStatus()){
            isLike = true;
        }
        User user = userService.findById(comment.getFromUid());
        List<Reply> replyList = replyService.findAllByCommentId(comment.getId());
        List<ReplyDTO> replyDTOList = new ArrayList<>();
        replyList.forEach(reply -> {
            boolean replyLike = false;
            ReplyLike replyLike1 = (ReplyLike) replyLikeService.status(reply.getId(),getUid(),null);
            if (replyLike1!=null&&replyLike1.isStatus()){
                replyLike = true;
            }
            User fromUser = userService.findById(reply.getFromUid());
            User toUser = userService.findById(reply.getToUid());
            ReplyDTO replyDTO = new ReplyDTO(reply,replyLike,
                    replyLikeService.countAllById(reply.getId()),
                    new SimpleUserDTO(fromUser.getId(),fromUser.getUserInfo().getNickname(),
                            fromUser.getUserInfo().getHeadImgUrl()),
                    new SimpleUserDTO(toUser.getId(),toUser.getUserInfo().getNickname(),
                            toUser.getUserInfo().getHeadImgUrl()));
            replyDTOList.add(replyDTO);
        });
        CommentDTO commentDTO = new CommentDTO(comment, isLike,
                user,replyDTOList);
        commentList.add(commentDTO);
    }

    private Long getUid(){
        User user = userService.findByUsername(UserUtil.getUserName());
        if (user!=null){
            return user.getId();
        }
        return null;
    }

    private Sort sort(String order,String sort){
        Sort st;
        if ("asc".equals(order)){
            st = new Sort(Sort.Direction.ASC,sort);
        }else {
            st = new Sort(Sort.Direction.DESC,sort);
        }
        return st;
    }

}
