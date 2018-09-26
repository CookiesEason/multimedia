package com.example.multimedia.service.impl;

import com.example.multimedia.domian.Problem;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.repository.ProblemRepository;
import com.example.multimedia.service.ProblemService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author CookiesEason
 * 2018/09/10 19:14
 */
@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    @Override
    public ResultVo save(Long topicId, String reason, Topic topic) {
        Problem problem = new Problem();
        problem.setTopicId(topicId);
        problem.setReasons(reason);
        problem.setTopic(topic);
        problemRepository.save(problem);
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo update(Long id, String reason) {
        Optional<Problem> problemOptional = problemRepository.findById(id);
        problemOptional.ifPresent(problem -> {
            problem.setReasons(reason);
            problemRepository.save(problem);
        });
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo getById(Long topicId, Topic topic) {
        Problem problem = problemRepository.findByTopicIdAndTopic(topicId, topic);
        if (problem!=null){
            List<String> reasons = Arrays.asList(problem.getReasons().split(";"));
            return ResultVoUtil.success(reasons);
        }
       return ResultVoUtil.error(0,"发生错误");
    }

    @Override
    public ResultVo delete(Long topicId, Topic topic) {
        System.out.println(topicId);
        problemRepository.deleteByTopicIdAndTopic(topicId, topic);
        return ResultVoUtil.success();
    }

}
