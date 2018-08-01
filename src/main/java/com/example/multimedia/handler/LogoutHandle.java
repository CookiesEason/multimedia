package com.example.multimedia.handler;

import com.alibaba.fastjson.JSON;
import com.example.multimedia.util.CookieUtil;
import com.example.multimedia.util.JwtUtil;
import com.example.multimedia.util.ResultVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CookiesEason
 * 2018/05/20 19:43
 */
public class LogoutHandle extends SimpleUrlLogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        String res = JSON.toJSONString(ResultVoUtil.success());
        CookieUtil.set(response, JwtUtil.TOKEN_PREFIX,null,0);
        response.getWriter().write(res);
    }
}
