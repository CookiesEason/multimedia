package com.example.multimedia.handler;

import com.alibaba.fastjson.JSON;
import com.example.multimedia.util.ResultVoUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author CookiesEason
 * 2018/07/24 14:08
 */
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(JSON.toJSON(ResultVoUtil.success()));
    }
}
