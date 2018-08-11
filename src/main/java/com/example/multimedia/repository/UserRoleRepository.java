package com.example.multimedia.repository;

import com.example.multimedia.domian.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/07/30 10:53
 * 用户角色
 */
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    UserRole findByRole(String name);

    List<UserRole> findAllByRoleLike(String role);

}
