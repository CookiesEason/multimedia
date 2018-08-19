package com.example.multimedia.filter;

import com.example.multimedia.exception.UserException;
import com.example.multimedia.util.CookieUtil;
import com.example.multimedia.util.JwtUtil;
import com.example.multimedia.service.impl.SecurityUserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CookiesEason
 * 2018/08/01 10:37
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityUserImpl securityUser;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        //String token = httpServletRequest.getHeader(JwtUtil.TOKEN_HEADER);
        Cookie cookie = CookieUtil.get(httpServletRequest,"Bearer");
        if (cookie!=null){
            String token = cookie.getValue();
            logger.info(token);
            if (token !=null){
                String username = JwtUtil.getUsername(token);
                logger.info("username="+username);
                if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
                    try {
                        UserDetails userDetails = this.securityUser.loadUserByUsername(username);
                        if (JwtUtil.parseToken(token)){
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                                    httpServletRequest));
                            logger.info("authenticated user " + username + ", setting security context");
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }catch (UserException e){
                        log.info(e.getMessage());
                    }
                }
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
