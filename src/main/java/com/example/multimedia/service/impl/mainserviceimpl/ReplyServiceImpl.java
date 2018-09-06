package com.example.multimedia.service.impl.mainserviceimpl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.Comment;
import com.example.multimedia.domian.maindomian.Reply;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.dto.ReplyDTO;
import com.example.multimedia.dto.SimpleUserDTO;
import com.example.multimedia.repository.ReplyRepository;
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
import java.util.Optional;

/**
 * @author CookiesEason
 * 2018/08/05 20:03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private SearchService searchService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private CommentService commentService;

    @Autowired
    @Qualifier(value = "ReplyLikeService")
    private LikeService replyLikeService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeService noticeService;

    @Override
    public Reply findById(Long id) {
        Optional<Reply> videoReply = replyRepository.findById(id);
        return videoReply.orElse(null);
    }

    @Override
    public ResultVo reply(Long commentId, String content, Long toUid) {
        Reply reply = new Reply();
        Comment comment = (Comment) commentService.findById(commentId);
        if (commentService.findById(commentId)==null){
            return ResultVoUtil.error(0,"评论不存在,无法进行回复");
        }
        Long userId = getUid();
        reply.setCommentId(commentId);
        reply.setFromUid(userId);
        reply.setToUid(toUid);
        reply.setContent(content);
        Reply rs = replyRepository.save(reply);
        ReplyDTO replyDTO = new ReplyDTO(rs,0L,new SimpleUserDTO(userService.findById(userId)),
                new SimpleUserDTO(userService.findById(toUid)));
        String title;
        if (comment.getTopic().equals(Topic.VIDEO)){
            title = videoService.findById(comment.getTopId()).getTitle();
        }else {
            title = articleService.findById((long)comment.getTopId()).getTitle();
        }
        noticeService.saveNotice(comment.getTopic(), comment.getTopId(),title,commentId,comment.getContent(),
                reply.getContent(),userId, toUid,"reply");
        return ResultVoUtil.success(replyDTO);
    }

    @Override
    public List<Reply> findAllByCommentId(Long id) {
        return replyRepository.findAllByCommentId(id);
    }

    @Override
    public void deleteById(Long id) {
        replyRepository.deleteByIdAndFromUid(id,getUid());
        searchService.deleteReplyById(id);
        replyLikeService.deleteAllById(id);
    }

    @Override
    public void deleteAllByCommentId(Long commentId) {
        List<Reply> videoReplies = replyRepository.deleteAllByCommentId(commentId);
        searchService.deleteReplyAllByCommentId(commentId);
        List<Long> ids = new ArrayList<>();
        for (Reply reply : videoReplies){
            Long id = reply.getId();
            ids.add(id);
        }
        replyLikeService.deleteAllByIds(ids);
    }

    @Override
    public void deleteAllByCommentIdIn(List<Long> ids) {
        List<Reply> videoReplies = replyRepository.deleteAllByCommentIdIn(ids);
        if (ids.size()>0){
            searchService.deleteReplyAllByComment_idIn(ids);
        }
        List<Long> replyIds = new ArrayList<>();
        for (Reply reply : videoReplies){
            Long id = reply.getId();
            replyIds.add(id);
        }
        replyLikeService.deleteAllByIds(replyIds);
    }

    @Override
    public ResultVo findAll(int page, int size, String order,String sort) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<Reply> videoReplies = replyRepository.findAll(pageable);
        List<ReplyDTO> replyDTOList = new ArrayList<>();
        videoReplies.getContent().forEach(videoReply -> {
            User user = userService.findById(videoReply.getFromUid());
            ReplyDTO replyDTO = new ReplyDTO(videoReply,
                    replyLikeService.countAllById(videoReply.getId()),
                    new SimpleUserDTO(user.getId(),user.getUserInfo().getNickname(),
                            user.getUserInfo().getHeadImgUrl()));
            replyDTOList.add(replyDTO);
        });
        PageDTO<ReplyDTO> replies = new PageDTO<>(replyDTOList,videoReplies.getTotalElements(),
                (long)videoReplies.getTotalPages());
        return ResultVoUtil.success(replies);
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
