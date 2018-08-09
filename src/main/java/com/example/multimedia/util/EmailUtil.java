package com.example.multimedia.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

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

    public static String generateCode(){
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb=new StringBuilder(4);
        for(int i=0;i<4;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

}
