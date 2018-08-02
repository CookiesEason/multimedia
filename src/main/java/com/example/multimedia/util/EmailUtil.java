package com.example.multimedia.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * @author CookiesEason
 * 2018/08/02 11:50
 */
public class EmailUtil {

    public static final int DAY = 86400000;

    public static String generateActivateCode(String username){
        return encrypt(username);
    }

    private static String encrypt(String username){
        return new BCryptPasswordEncoder().encode(username);
    }

}
