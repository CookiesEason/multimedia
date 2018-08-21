package com.example.multimedia.service;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.vo.ResultVo;

/**
 * @author CookiesEason
 * 2018/08/20 20:36
 */
public interface AdminNoticeService {

    void save(Long topicId, Topic topic, String title);

    ResultVo getAdminNotice(int page);

    void deleteById(Long id);

    ResultVo unRead();

}
