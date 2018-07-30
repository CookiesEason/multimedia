package com.example.multimedia.repository;

import com.example.multimedia.domian.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CookiesEason
 * 2018/07/30 10:41
 * 用户信息
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

    UserInfo findByNickname(String nickname);

}
