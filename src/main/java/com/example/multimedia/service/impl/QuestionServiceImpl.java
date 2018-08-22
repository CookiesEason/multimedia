package com.example.multimedia.service.impl;

import com.example.multimedia.domian.Question;
import com.example.multimedia.domian.User;
import com.example.multimedia.dto.question.QuestionDTO;
import com.example.multimedia.dto.question.TestDTO;
import com.example.multimedia.repository.QuestionRepository;
import com.example.multimedia.service.QuestionService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author CookiesEason
 * 2018/08/16 14:33
 */
@Service
public class QuestionServiceImpl implements QuestionService {


    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Override
    public ResultVo save(List<Question> questionList) {
        questionRepository.saveAll(questionList);
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo update(Question question) {
        questionRepository.save(question);
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo delete(Long id) {
         questionRepository.deleteById(id);
         return ResultVoUtil.success();
    }

    @Override
    public ResultVo getTest() {
        List<Question> questionList = questionRepository.findByRand();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
       valueOperations.set("Q"+UserUtil.getUserName(),questionList,60,TimeUnit.MINUTES);
        return ResultVoUtil.success(questionRepository.findByRand());
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResultVo checkAnswer(List<Character> answers) {
        if (answers.size()<10){
            return ResultVoUtil.error(0,"您还有未作答的题目");
        }
        List<Question> questionList = (List<Question>) redisTemplate.opsForValue().get("Q"+UserUtil.getUserName());
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        int correctNum = 0;
        if (questionList==null){
            return ResultVoUtil.error(0,"发生异常，请稍后重试");
        }
        questionList.forEach(question -> {
            QuestionDTO questionDTO = new QuestionDTO(question);
            questionDTOS.add(questionDTO);
        });
        for (int i=0;i<answers.size();i++){
            questionDTOS.get(i).setChooseAnswer(answers.get(i));
            questionDTOS.get(i).setCorrectAnswer(questionList.get(i).getAnswer());
            if (questionDTOS.get(i).getChooseAnswer().equals(questionDTOS.get(i).getCorrectAnswer())){
                questionDTOS.get(i).setCorrect(true);
                correctNum++;
            }else {
                questionDTOS.get(i).setCorrect(false);
            }
        }
        if (correctNum>=8){
            userService.changeRole(getUid(),"ROLE_SENIOR_USER");
        }
        TestDTO test = new TestDTO((long)questionList.size(),(long)correctNum,questionDTOS);
        redisTemplate.delete("Q"+UserUtil.getUserName());
        return ResultVoUtil.success(test);
    }

    private Long getUid(){
        User user = userService.findByUsername(UserUtil.getUserName());
        if (user!=null){
            return user.getId();
        }
        return null;
    }


}


