package com.example.multimedia.service;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.vo.ResultVo;

/**
 * @author CookiesEason
 * 2018/08/14 20:20
 */
public interface NoticeService {

    ResultVo saveNotice(Topic topic,Long topicId,String title,Long commentId,String comment,
                        String reply,Long fromUid,Long toUid,String type);

    ResultVo getNotices(int page);

    void deleteById(Long messageId);

    ResultVo unRead();

}
