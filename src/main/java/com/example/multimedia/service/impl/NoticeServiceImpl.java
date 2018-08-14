package com.example.multimedia.service.impl;

import com.example.multimedia.domian.Notice;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.dto.NoticeDTO;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.repository.NoticeRepository;
import com.example.multimedia.service.NoticeService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/14 20:25
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    private static final int SIZE = 10;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserService userService;

    @Override
    public ResultVo saveNotice(Topic topic,Long contentId,Long fromUid,Long toUid,String type) {
        Notice notice = new Notice();
        notice.setTopic(topic);
        notice.setFromUid(fromUid);
        notice.setToUid(toUid);
        notice.setType(type);
        notice.setContentId(contentId);
        noticeRepository.save(notice);
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo getNotices(int page) {
        Pageable pageable = PageRequest.of(page,SIZE);
        Page<Notice> noticePage = noticeRepository.findAllByToUid(getUid(),pageable);
        List<NoticeDTO> notices = new ArrayList<>();
        noticePage.getContent().forEach(notice -> {
            if (notice.getTopic().equals(Topic.FOLLOW)){
                NoticeDTO message = new NoticeDTO(userService.findById(notice.getFromUid()).getUsername(),
                        "/api/user/"+notice.getFromUid(),"关注了你");
                notices.add(message);
            }
        });
        PageDTO<NoticeDTO> messages = new PageDTO<>(notices,noticePage.getTotalElements(),(long)noticePage.getTotalPages());
        return ResultVoUtil.success(messages);
    }

    private Long getUid(){
        return userService.findByUsername(UserUtil.getUserName()).getId();
    }

}
