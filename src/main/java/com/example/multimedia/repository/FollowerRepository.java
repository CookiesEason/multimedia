package com.example.multimedia.repository;

import com.example.multimedia.domian.Follower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author CookiesEason
 * 2018/08/14 14:26
 */
public interface FollowerRepository extends JpaRepository<Follower,Long> {

    Follower findByUserIdAndFollowerId(Long userId,Long followerId);

    Page<Follower> findAllByUserId(Long userId, Pageable pageable);

    Page<Follower> findAllByFollowerId(Long followerId,Pageable pageable);

}
