package com.example.multimedia.config;

import com.example.multimedia.exception.UserException;
import com.example.multimedia.filter.SensitiveWordFiler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author CookiesEason
 * 2018/09/20 11:25
 */
@Aspect
@Component
public class SensitiveAop {

    @Pointcut("execution(public * com.example.multimedia.service.CommentService.createComment(..)))")
    public void commentService() {

    }

    @Pointcut("execution(public * com.example.multimedia.service.ReplyService.reply(..)))")
    public void replyService() {

    }

    @Pointcut("execution(public * com.example.multimedia.service.ArticleService.save(..)))")
    public void articleService() {

    }

    @Before("commentService()")
    public void doBeforeCommentService(JoinPoint joinPoint) {
        sensitive(joinPoint);
    }

    @Before("replyService()")
    public void doBeforeReplyService(JoinPoint joinPoint) {
        sensitive(joinPoint);
    }

    @Before("articleService()")
    public void doBeforeArticleService(JoinPoint joinPoint) {
        System.out.println("处理敏感词");
        //获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        System.out.println(obj);
        SensitiveWordFiler sw = new SensitiveWordFiler();
        sw.InitializationWork();
        sw.filterInfo(obj[0].toString());
        System.out.println(SensitiveWordFiler.sensitiveWordList);
        if (SensitiveWordFiler.sensitiveWordList.size() > 0) {
            throw new UserException("包含敏感词,请修改", 501);
        }
        sw.filterInfo(obj[1].toString());
        System.out.println(SensitiveWordFiler.sensitiveWordList);
        if (SensitiveWordFiler.sensitiveWordList.size() > 0) {
            throw new UserException("包含敏感词,请修改", 501);
        }
    }

    private void sensitive(JoinPoint joinPoint) {
        System.out.println("处理敏感词");
        //获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        SensitiveWordFiler sw = new SensitiveWordFiler();
        sw.InitializationWork();
        sw.filterInfo(obj[1].toString());
        System.out.println(SensitiveWordFiler.sensitiveWordList);
        if (SensitiveWordFiler.sensitiveWordList.size() > 0) {
            throw new UserException("包含敏感词,请修改", 501);
        }
    }
}
