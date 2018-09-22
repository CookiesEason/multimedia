package com.example.multimedia.service;

import com.example.multimedia.domian.AdminNotice;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.vo.ResultVo;

/**
 * @author CookiesEason
 * 2018/08/20 20:36
 */
public interface AdminNoticeService {

    void save(Long topicId, Topic topic, String title,String type);

    void save(Long topicId, Topic topic, String title,String reason,String reasonCntent,String type);

    ResultVo getAdminNotice(int page);

    PageDTO<AdminNotice> getReportNotice(int page, String type);

    void deleteById(Long id);

    ResultVo unRead();

}
