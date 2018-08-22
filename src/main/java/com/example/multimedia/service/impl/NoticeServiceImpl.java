package com.example.multimedia.service.impl;

import com.example.multimedia.domian.Notice;
import com.example.multimedia.domian.User;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.dto.NoticeDTO;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.repository.NoticeRepository;
import com.example.multimedia.service.*;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
 * 2018/08/14 20:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NoticeServiceImpl implements NoticeService {

    private static final int SIZE = 10;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReplyService replyService;

    @Override
    @CacheEvict(value = "message",allEntries = true)
    public ResultVo saveNotice(Topic topic,Long topicId,String title,Long commentId,String comment,
                               String reply, Long fromUid,Long toUid,String type) {
        Notice notice = new Notice();
        notice.setTopic(topic);
        notice.setTopicId(topicId);
        notice.setTitle(title);
        notice.setFromUid(fromUid);
        notice.setToUid(toUid);
        notice.setType(type);
        notice.setCommentId(commentId);
        notice.setComment(comment);
        notice.setReply(reply);
        noticeRepository.save(notice);
        return ResultVoUtil.success();
    }

    @Override
    @Cacheable(value = "message")
    public ResultVo getNotices(int page) {
        Sort sort = new Sort(Sort.Direction.DESC,"date");
        Pageable pageable = PageRequest.of(page,SIZE,sort);
        Page<Notice> noticePage = noticeRepository.findAllByToUid(getUid(),pageable);
        List<Notice> noticeList = new ArrayList<>();
        List<NoticeDTO> notices = new ArrayList<>();
        noticePage.getContent().forEach(notice -> {
            notice.setReaded(true);
            noticeList.add(notice);
            if (notice.getTopic().equals(Topic.FOLLOW)){
                NoticeDTO message = new NoticeDTO(notice.getId(),userService.findById(notice.getFromUid()).getUsername(),
                        "/api/user/"+notice.getFromUid(),"关注了你",notice.getDate());
                notices.add(message);
            }
            if (notice.getTopic().equals(Topic.VIDEO)){
                if ("videoPraise".equals(notice.getType())){
                    NoticeDTO message = new NoticeDTO(notice.getId(),userService.findById(notice.getFromUid()).getUsername(),
                            "/api/user/"+notice.getFromUid(),"点赞了你的视频",
                           notice.getTitle(), "/video/"+notice.getTopicId()
                            ,notice.getDate());
                    notices.add(message);
                }else if ("comment".equals(notice.getType())){
                    videoCommentNotice(notices, notice,"评论了你的视频");
                }else if ("commentPraise".equals(notice.getType())){
                    videoCommentNotice(notices,notice,"点赞了你的评论");
                }else if ("reply".equals(notice.getType())){
                    videoReplyNotice(notices, notice,"回复了你的评论");
                }else if ("enable".equals(notice.getType())){
                    passNotice(notices, notice, "你的视频已经通过审核",
                            "/videos/"+notice.getTopic());
                }else if ("unEnable".equals(notice.getType())) {
                    passNotice(notices, notice, "你的视频未通过审核",
                            null);
                }else{
                    videoReplyNotice(notices, notice,"点赞了你的回复");
                }
            }else if(notice.getTopic().equals(Topic.ARTICLE)){
                if ("articlePraise".equals(notice.getType())){
                    NoticeDTO message = new NoticeDTO(notice.getId(),userService.findById(notice.getFromUid()).getUsername(),
                            "/api/user/"+notice.getFromUid(),"点赞了你的文章",
                            articleService.findById((long)notice.getTopicId()).getTitle(),
                            "/article/"+notice.getTopicId()
                            ,notice.getDate());
                    notices.add(message);
                }else if ("comment".equals(notice.getType())){
                    articleCommentNotice(notices, notice,"评论了你的文章");
                }else if ("commentPraise".equals(notice.getType())){
                    articleCommentNotice(notices,notice,"点赞了你的评论");
                }else if ("reply".equals(notice.getType())){
                   articleReplyNotice(notices,notice,"回复了你的评论");
                }else if ("enable".equals(notice.getType())){
                    passNotice(notices, notice, "你的文章已经通过审核",
                            "/articles/"+notice.getTopic());
                } else if ("unEnable".equals(notice.getType())) {
                    passNotice(notices, notice, "你的文章未通过审核",
                            null);
                } else {
                    articleReplyNotice(notices, notice, "点赞了你的回复");
                }
            }
        });
        PageDTO<NoticeDTO> messages = new PageDTO<>(notices,noticePage.getTotalElements(),(long)noticePage.getTotalPages());
        noticeRepository.saveAll(noticeList);
        return ResultVoUtil.success(messages);
    }

    private void passNotice(List<NoticeDTO> notices, Notice notice, String content, String url) {
        NoticeDTO message = new NoticeDTO(notice.getId(), null, null
                , content, notice.getTitle(), url, notice.getDate());
        notices.add(message);
    }

    private void videoCommentNotice(List<NoticeDTO> notices, Notice notice,String content) {
        NoticeDTO message = new NoticeDTO(notice.getId(),userService.findById(notice.getFromUid()).getUsername(),
                "/api/user/"+notice.getFromUid(),content,
                videoService.findById(notice.getTopicId()).getTitle(),
                "/video/"+notice.getTopicId(),notice.getCommentId(),notice.getComment()
                ,notice.getDate());
        notices.add(message);
    }

    private void videoReplyNotice(List<NoticeDTO> notices, Notice notice,String content) {
        NoticeDTO message = new NoticeDTO(notice.getId(),userService.findById(notice.getFromUid()).getUsername(),
                "/api/user/"+notice.getFromUid(),content,
                videoService.findById(notice.getTopicId()).getTitle(),
                "/video/"+notice.getTopicId(),notice.getCommentId(),notice.getComment()
                ,notice.getReply(),notice.getDate());
        notices.add(message);
    }

    private void articleCommentNotice(List<NoticeDTO> notices, Notice notice,String content) {
        NoticeDTO message = new NoticeDTO(notice.getId(),userService.findById(notice.getFromUid()).getUsername(),
                "/api/user/"+notice.getFromUid(),content,
                articleService.findById((long)notice.getTopicId()).getTitle(),
                "/article/"+notice.getTopicId(),notice.getCommentId(),notice.getComment()
                ,notice.getDate());
        notices.add(message);
    }

    private void articleReplyNotice(List<NoticeDTO> notices, Notice notice,String content) {
        NoticeDTO message = new NoticeDTO(notice.getId(),userService.findById(notice.getFromUid()).getUsername(),
                "/api/user/"+notice.getFromUid(),content,
                articleService.findById((long)notice.getTopicId()).getTitle(),
                "/article/"+notice.getTopicId(),notice.getCommentId(),notice.getComment()
                ,notice.getReply(),notice.getDate());
        notices.add(message);
    }

    @Override
    @CacheEvict(value = "message",allEntries = true)
    public void deleteById(Long messageId) {
        noticeRepository.deleteByIdAndToUid(messageId,getUid());
    }

    @Override
    public ResultVo unRead() {
        Long counts = noticeRepository.countAllByToUidAndReaded(getUid(),false);
        return ResultVoUtil.success(counts);
    }

    private Long getUid(){
        User user = userService.findByUsername(UserUtil.getUserName());
        if (user!=null){
            return user.getId();
        }
        return null;
    }

}
