package com.example.multimedia.controller;

import com.example.multimedia.service.QuestionService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/16 14:56
 */
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    private ResultVo findByRand(){
        return questionService.getTest();
    }

    @PostMapping("/answer")
    private ResultVo answer(@RequestBody List<Character> answers){
        return ResultVoUtil.success(questionService.checkAnswer(answers));
    }


}
