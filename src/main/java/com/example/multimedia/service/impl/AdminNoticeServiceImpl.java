package com.example.multimedia.service.impl;

import com.example.multimedia.domian.AdminNotice;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.repository.AdminNoticeRepository;
import com.example.multimedia.service.AdminNoticeService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/20 20:37
 */
@Service
public class AdminNoticeServiceImpl implements AdminNoticeService {

    @Autowired
    private AdminNoticeRepository adminNoticeRepository;

    @Override
    public void save(Long topicId, Topic topic, String title,String type) {
        AdminNotice adminNotice = new AdminNotice();
        adminNotice.setTopicId(topicId);
        adminNotice.setTitle(title);
        adminNotice.setTopic(topic);
        adminNotice.setType(type);
        adminNoticeRepository.save(adminNotice);
    }

    @Override
    public void save(Long topicId, Topic topic, String title, String reason, String reasonCntent, String type) {
        AdminNotice adminNotice = new AdminNotice();
        adminNotice.setTopicId(topicId);
        adminNotice.setTitle(title);
        adminNotice.setTopic(topic);
        adminNotice.setReason(reason);
        adminNotice.setReasonContent(reasonCntent);
        adminNotice.setType(type);
        adminNoticeRepository.save(adminNotice);
    }

    @Override
    public ResultVo getAdminNotice(int page) {
        int size = 20;
        Sort sort = new Sort(Sort.Direction.DESC,"date");
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<AdminNotice> adminNoticePage = adminNoticeRepository.findAll(pageable);
        List<AdminNotice> adminNoticeList = new ArrayList<>();
        adminNoticePage.getContent().forEach(adminNotice -> {
            adminNotice.setReaded(true);
            adminNoticeList.add(adminNotice);
        });
        PageDTO<AdminNotice> adminNotices = new PageDTO<>(adminNoticePage.getContent(),adminNoticePage.getTotalElements(),
                (long)adminNoticePage.getTotalPages());
        adminNoticeRepository.saveAll(adminNoticeList);
        return ResultVoUtil.success(adminNotices);
    }

    @Override
    public  PageDTO<AdminNotice> getReportNotice(int page, String type) {
        Pageable pageable = PageRequest.of(page-1,16, Sort.Direction.DESC,"date");
        Page<AdminNotice> adminNotices = adminNoticeRepository.findAllByType(type,pageable);
        return new PageDTO<>(adminNotices.getContent(),adminNotices.getTotalElements(),
                (long)adminNotices.getTotalPages());
    }

    @Override
    public void deleteById(Long id) {
        adminNoticeRepository.deleteById(id);
    }

    @Override
    public ResultVo unRead() {
        return ResultVoUtil.success(adminNoticeRepository.countAllByReaded(false));
    }
}
