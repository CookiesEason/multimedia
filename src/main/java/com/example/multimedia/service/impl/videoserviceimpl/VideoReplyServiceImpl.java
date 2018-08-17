package com.example.multimedia.service.impl.videoserviceimpl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.videodomian.VideoComment;
import com.example.multimedia.domian.videodomian.VideoReply;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.dto.ReplyDTO;
import com.example.multimedia.dto.SimpleUserDTO;
import com.example.multimedia.repository.VideoReplyRepository;
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
@Service(value ="VideoReplyService")
@Transactional(rollbackFor = Exception.class)
public class VideoReplyServiceImpl implements ReplyService {

    @Autowired
    private VideoReplyRepository videoReplyRepository;

    @Autowired
    private VideoSearchService videoSearchService;

    @Autowired
    @Qualifier(value = "VideoCommentService")
    private CommentService commentService;

    @Autowired
    @Qualifier(value = "VideoReplyLikeService")
    private LikeService videoReplyLikeService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeService noticeService;

    @Override
    public VideoReply findById(Long id) {
        Optional<VideoReply> videoReply = videoReplyRepository.findById(id);
        return videoReply.orElse(null);
    }

    @Override
    public ResultVo reply(Long commentId, String content, Long toUid) {
        VideoReply videoReply = new VideoReply();
        VideoComment videoComment = (VideoComment) commentService.findById(commentId);
        if (commentService.findById(commentId)==null){
            return ResultVoUtil.error(0,"评论不存在,无法进行回复");
        }
        videoReply.setCommentId(commentId);
        videoReply.setFromUid(getUid());
        videoReply.setToUid(toUid);
        videoReply.setContent(content);
        videoReplyRepository.save(videoReply);
        noticeService.saveNotice(Topic.VIDEO,videoComment.getVideoId(),commentId,videoReply.getId(),getUid(),
                toUid,"reply");
        return ResultVoUtil.success();
    }

    @Override
    public List<VideoReply> findAllByCommentId(Long id) {
        return videoReplyRepository.findAllByCommentId(id);
    }

    @Override
    public void deleteById(Long id) {
        videoReplyRepository.deleteByIdAndFromUid(id,getUid());
        videoSearchService.deleteReplyById(id);
        videoReplyLikeService.deleteAllById(id);
    }

    @Override
    public void deleteAllByCommentId(Long commentId) {
        List<VideoReply> videoReplies = videoReplyRepository.deleteAllByCommentId(commentId);
        videoSearchService.deleteReplyAllByCommentId(commentId);
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
        if (ids.size()>0){
            videoSearchService.deleteReplyAllByComment_idIn(ids);
        }
        List<Long> replyIds = new ArrayList<>();
        for (VideoReply videoReply : videoReplies){
            Long id = videoReply.getId();
            replyIds.add(id);
        }
        videoReplyLikeService.deleteAllByIds(replyIds);
    }

    @Override
    public ResultVo findAll(int page, int size, String order,String sort) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<VideoReply> videoReplies = videoReplyRepository.findAll(pageable);
        List<ReplyDTO> replyDTOList = new ArrayList<>();
        videoReplies.getContent().forEach(videoReply -> {
            ReplyDTO replyDTO = new ReplyDTO(videoReply,
                    videoReplyLikeService.countAllById(videoReply.getId()),
                    new SimpleUserDTO(userService.findById(videoReply.getFromUid())));
            replyDTOList.add(replyDTO);
        });
        PageDTO<ReplyDTO> replies = new PageDTO<>(replyDTOList,videoReplies.getTotalElements(),
                (long)videoReplies.getTotalPages());
        return ResultVoUtil.success(replies);
    }

    private Long getUid(){
        return userService.findByUsername(UserUtil.getUserName()).getId();
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
