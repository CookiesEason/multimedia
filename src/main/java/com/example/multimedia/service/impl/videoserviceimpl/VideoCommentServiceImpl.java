package com.example.multimedia.service.impl.videoserviceimpl;

import com.example.multimedia.domian.Notice;
import com.example.multimedia.domian.User;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.videodomian.VideoComment;
import com.example.multimedia.domian.videodomian.VideoReply;
import com.example.multimedia.domian.abstractdomian.AbstractComment;
import com.example.multimedia.dto.CommentDTO;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.dto.ReplyDTO;
import com.example.multimedia.dto.SimpleUserDTO;
import com.example.multimedia.repository.VideoCommentRepository;
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
@Service(value = "VideoCommentService")
@Transactional(rollbackFor = Exception.class)
public class VideoCommentServiceImpl implements CommentService {

    @Autowired
    private VideoCommentRepository videoCommentRepository;

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoSearchService videoSearchService;

    @Autowired
    @Qualifier(value = "VideoReplyService")
    private ReplyService replyService;

    @Autowired
    @Qualifier(value = "VideoCommentLikeService")
    private LikeService videoCommentLikeService;

    @Autowired
    @Qualifier(value = "VideoReplyLikeService")
    private LikeService videoReplyLikeService;

    @Autowired
    @Qualifier(value = "VideoReplyService")
    private ReplyService videoReplyService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserService userService;

    @Override
    public ResultVo createComment(Long videoId,String content) {
        VideoComment videoComment = new VideoComment();
        if (videoService.findById(videoId)==null){
            return ResultVoUtil.error(0,"视频不存在,无法进行评论");
        }
        Long fromUid = userService.findByUsername(UserUtil.getUserName()).getId();
        videoComment.setVideoId(videoId);
        videoComment.setContent(content);
        videoComment.setFromUid(fromUid);
        videoCommentRepository.save(videoComment);
        Long toUid = videoService.findById(videoId).getUserId();
        if (!fromUid.equals(toUid)){
            noticeService.saveNotice(Topic.VIDEO,videoId,videoComment.getId(),null,fromUid,toUid,"comment");
        }
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo getComments(Long videoId,int commentPage) {
        int size=10;
        Pageable pageable = PageRequest.of(commentPage,size);
        Page<VideoComment> videoComments = videoCommentRepository.findAllByVideoId(pageable,videoId);
        List<CommentDTO> commentList = new ArrayList<>();
        videoComments.getContent().forEach(comment -> {
            User user = userService.findById(comment.getFromUid());
            List<VideoReply> replyList = replyService.findAllByCommentId(comment.getId());
            List<ReplyDTO> replyDTOList = new ArrayList<>();
            replyList.forEach(videoReply -> {
                ReplyDTO replyDTO = new ReplyDTO(videoReply,
                        videoReplyLikeService.countAllById(videoReply.getId()),
                        new SimpleUserDTO(userService.findById(videoReply.getFromUid())),
                        new SimpleUserDTO(userService.findById(videoReply.getToUid())));
                replyDTOList.add(replyDTO);
            });
            CommentDTO commentDTO = new CommentDTO(comment,videoCommentLikeService.countAllById(comment.getId()),
                    user,replyDTOList);
            commentList.add(commentDTO);
        });
        PageDTO<CommentDTO> comments = new PageDTO<>(commentList,videoComments.getTotalElements(),
                (long) videoComments.getTotalPages());
        return ResultVoUtil.success(comments);
    }

    @Override
    public AbstractComment findById(Long id) {
        return videoCommentRepository.findVideoCommentById(id);
    }

    @Override
    public void deleteById(Long id) {
        Long deleteId = videoCommentRepository.deleteByIdAndFromUid(id,getUid());
        videoSearchService.deleteCommentById(id);
        if (deleteId!=0){
            replyService.deleteAllByCommentId(id);
        }
        videoCommentLikeService.deleteAllById(id);
    }

    @Override
    public void deleteAllBycontentId(Long videoId) {
        videoSearchService.deleteAllByVideoId(videoId);
        List<VideoComment> videoComments = videoCommentRepository.deleteAllByVideoId(videoId);
        List<Long> ids= new ArrayList<>();
        for (VideoComment videoComment : videoComments){
           Long id =  videoComment.getId();
           ids.add(id);
        }
        videoReplyService.deleteAllByCommentIdIn(ids);
        videoCommentLikeService.deleteAllByIds(ids);
    }

    @Override
    public ResultVo findAll(int page, int size, String order,String sort) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<VideoComment> videoComments = videoCommentRepository.findAll(pageable);
        List<CommentDTO> commentList = new ArrayList<>();
       videoComments.getContent().forEach(videoComment -> {
           User user = userService.findById(videoComment.getFromUid());
           CommentDTO commentDTO = new CommentDTO(videoComment,
                   videoCommentLikeService.countAllById(videoComment.getId()), user);
           commentList.add(commentDTO);
       });
        PageDTO<CommentDTO> comments = new PageDTO<>(commentList,videoComments.getTotalElements(),
                (long) videoComments.getTotalPages());
        return ResultVoUtil.success(comments);
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
