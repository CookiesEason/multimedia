package com.example.multimedia.handler;

import com.alibaba.fastjson.JSON;
import com.example.multimedia.util.ResultVoUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CookiesEason
 * 2018/05/18 19:29
 */
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        String res = JSON.toJSONString(ResultVoUtil.error(0,"账号或密码错误"));
        if (exception.getCause()!=null){
            res = JSON.toJSONString(ResultVoUtil.error(0,exception.getMessage()));
        }
        response.getWriter().print(res);
    }
}
