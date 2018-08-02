package com.example.multimedia.repository;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CookiesEason
 * 2018/07/23 15:19
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    User findByUserInfoNickname(String nickname);

    User findByEmail(String email);
}
