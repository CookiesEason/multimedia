package com.example.multimedia.repository;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserInfo;
import com.example.multimedia.domian.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/07/23 15:19
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findUserById(Long id);

    User findByUsername(String username);

    User findByUserInfoNickname(String nickname);

    User findByEmail(String email);

    Page<User> findAllByRoleListIn(Pageable pageable, List<UserRole> userRoles);

    List<User> findByUsernameOrEmailOrUserInfoNickname(String username, String email, String nickname);

    List<User> findUsersByIdIn(List<Long> ids);
}
