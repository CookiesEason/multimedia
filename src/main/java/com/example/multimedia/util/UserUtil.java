package com.example.multimedia.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author CookiesEason
 * 2018/08/04 11:40
 */
public class UserUtil {

    public static String getUserName(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }

}
