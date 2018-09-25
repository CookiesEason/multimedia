package com.example.multimedia.handler;

import com.alibaba.fastjson.JSON;
import com.example.multimedia.domian.User;
import com.example.multimedia.dto.SimpleUserDTO;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.JwtUtil;
import com.example.multimedia.util.CookieUtil;
import com.example.multimedia.util.ResultVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CookiesEason
 * 2018/07/24 14:08
 */
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        String token = JwtUtil.generateToken(authentication);
        CookieUtil.set(response, JwtUtil.TOKEN_PREFIX,token,9600);
        User user = service.findByUsername(authentication.getName());
        SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user.getId(),user.getUserInfo().getNickname(),
                user.getUserInfo().getHeadImgUrl(),authentication.getAuthorities().toArray()[0].toString());
        response.getWriter().print(JSON.toJSON(ResultVoUtil.success(simpleUserDTO)));
    }
}
