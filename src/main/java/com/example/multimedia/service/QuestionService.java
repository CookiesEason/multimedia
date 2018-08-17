package com.example.multimedia.service;

import com.example.multimedia.domian.Question;
import com.example.multimedia.vo.ResultVo;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/16 14:29
 */
public interface QuestionService {

    /**
     * 上传题目
     * @param questionList
     * @return
     */
    ResultVo save(List<Question> questionList);

    /**
     * 更新题目
     * @param question
     * @return
     */
    ResultVo update(Question question);

    /**
     * 删除题目
     * @param id
     * @return
     */
    ResultVo delete(Long id);

    /**
     * 获取10道题目
     * @return
     */
    ResultVo getTest();

    /**
     * 验证答案
     * @param answers
     * @return
     */
    ResultVo checkAnswer(List<Character> answers);

}
