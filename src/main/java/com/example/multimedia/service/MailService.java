package com.example.multimedia.service;

import com.example.multimedia.vo.ResultVo;
/**
 * @author CookiesEason
 * 2018/08/02 13:38
 */
public interface MailService {

   ResultVo sendEmail(String username,String activateCode);

   ResultVo  sendPasswordUpdateEmail();

}
