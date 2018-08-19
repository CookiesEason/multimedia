package com.example.multimedia.service.impl;

import com.example.multimedia.domian.Notice;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.dto.NoticeDTO;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.repository.NoticeRepository;
import com.example.multimedia.service.*;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private CommentService videoCommentService;

    @Autowired
    private ReplyService videoReplyService;

    @Override
    @CacheEvict(value = "message",allEntries = true)
    public ResultVo saveNotice(Topic topic,Long topicId,Long contentId,Long replyId,Long fromUid,Long toUid,String type) {
        Notice notice = new Notice();
        notice.setTopic(topic);
        notice.setTopicId(topicId);
        notice.setFromUid(fromUid);
        notice.setToUid(toUid);
        notice.setType(type);
        notice.setContentId(contentId);
        notice.setReplyId(replyId);
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
            }else if (notice.getTopic().equals(Topic.VIDEO)){
                if ("comment".equals(notice.getType())){
                    NoticeDTO message = new NoticeDTO(notice.getId(),userService.findById(notice.getFromUid()).getUsername(),
                            "/api/user/"+notice.getFromUid(),"评论了你的视频",
                            videoService.findById(notice.getTopicId()).getTitle(),
                            "/api/video/"+notice.getTopicId(),
                            notice.getContentId(),
                            videoCommentService.findById(notice.getContentId()).getContent(),notice.getDate());
                    notices.add(message);
                }else if ("reply".equals(notice.getType())){
                    NoticeDTO message = new NoticeDTO(notice.getId(),userService.findById(notice.getFromUid()).getUsername(),
                            "/api/user/"+notice.getFromUid(),"回复了你的评论",
                            videoService.findById(notice.getTopicId()).getTitle(),
                            "/api/video/"+notice.getTopicId(),
                            notice.getContentId(),notice.getReplyId(),
                            videoReplyService.findById(notice.getReplyId()).getContent(),notice.getDate());
                    notices.add(message);
                }else if ("videoPraise".equals(notice.getType())){
                    NoticeDTO message = new NoticeDTO(notice.getId(),userService.findById(notice.getFromUid()).getUsername(),
                            "/api/user/"+notice.getFromUid(),"点赞了你的视频",
                            videoService.findById(notice.getTopicId()).getTitle(),
                            "/api/video/"+notice.getTopicId(),notice.getDate());
                    notices.add(message);
                }else if ("commentPraise".equals(notice.getType())){
                    NoticeDTO message = new NoticeDTO(notice.getId(),userService.findById(notice.getFromUid()).getUsername(),
                            "/api/user/"+notice.getFromUid(),"点赞了你的评论",
                            videoService.findById(notice.getTopicId()).getTitle(),
                            "/api/video/"+notice.getTopicId(), notice.getContentId(),
                            videoCommentService.findById(notice.getContentId()).getContent(),notice.getDate());
                    notices.add(message);
                }else if ("replyPraise".equals(notice.getType())){
                    NoticeDTO message = new NoticeDTO(notice.getId(),userService.findById(notice.getFromUid()).getUsername(),
                            "/api/user/"+notice.getFromUid(),"点赞了你的回复",
                            videoService.findById(notice.getTopicId()).getTitle(),
                            "/api/video/"+notice.getTopicId(), notice.getContentId(),notice.getReplyId(),
                            videoReplyService.findById(notice.getReplyId()).getContent(),notice.getDate());
                    notices.add(message);
                }
            }
        });
        PageDTO<NoticeDTO> messages = new PageDTO<>(notices,noticePage.getTotalElements(),(long)noticePage.getTotalPages());
       noticeRepository.saveAll(noticeList);
        return ResultVoUtil.success(messages);
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
        return userService.findByUsername(UserUtil.getUserName()).getId();
    }

}
