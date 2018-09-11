package com.example.multimedia.service;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.vo.ResultVo;

/**
 * @author CookiesEason
 * 2018/09/10 19:14
 */
public interface ProblemService {

    //todo 编辑

    /**
     *
     * @param topicId
     * @param reason
     * @param topic
     * @return
     */
    ResultVo save(Long topicId, String reason, Topic topic);

    /**
     * 更新
     * @param id
     * @param reason
     * @return
     */
    ResultVo update(Long id,String reason);

    /**
     * 获取
     * @param topicId
     * @param topic
     * @return
     */
    ResultVo getById(Long topicId,Topic topic);

    /**
     * 删除
     * @param topicId
     * @param topic
     * @return
     */
    ResultVo delete(Long topicId,Topic topic);

}
