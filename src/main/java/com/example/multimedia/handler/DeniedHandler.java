package com.example.multimedia.handler;

import com.alibaba.fastjson.JSON;
import com.example.multimedia.util.ResultVoUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CookiesEason
 * 2018/08/02 20:27
 */
public class DeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(403);
//        httpServletResponse.setContentType("application/json; charset=utf-8");
//        httpServletResponse.getWriter().print(JSON.toJSON(ResultVoUtil.error(403,e.getMessage())));
        httpServletResponse.sendRedirect("/403");
    }
}
