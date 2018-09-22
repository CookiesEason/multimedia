package com.example.multimedia.repository;

import com.example.multimedia.domian.Follower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * @author CookiesEason
 * 2018/08/14 14:26
 */
public interface FollowerRepository extends JpaRepository<Follower,Long> {

    Follower findByUserIdAndFollowerId(Long userId,Long followerId);

    Page<Follower> findAllByUserIdAndStatus(Long userId, Boolean status,Pageable pageable);

    Page<Follower> findAllByFollowerIdAndStatus(Long followerId,Boolean status,Pageable pageable);

    @Query(value = "SELECT SUM(lt.num) as num from (\n" +
            "(select COUNT(*) num,date,DATE_FORMAT(date,'%Y-%m-%d') as dd\n" +
            "from follower \n" +
            "WHERE follower_id = :userId\n" +
            "GROUP BY DATE_FORMAT(date,'%y-%m-%d')) a\n" +
            "INNER JOIN\n" +
            "(select COUNT(*) num,date,DATE_FORMAT(date,'%Y-%m-%d') as dd\n" +
            "from follower \n" +
            "WHERE follower_id = :userId\n" +
            "GROUP BY DATE_FORMAT(date,'%y-%m-%d'))lt\n" +
            "ON a.dd >= lt.dd \n" +
            ")\n" +
            "WHERE to_days(now()) - to_days(a.date) >= 0\n" +
            "GROUP BY a.dd\n" +
            "LIMIT 5",nativeQuery = true)
    long [] fansNumByDays(@Param("userId") Long userId);

    Long countAllByUserIdAndStatus(Long userId,Boolean status);

    Long countAllByFollowerIdAndStatus(Long userId,Boolean status);

}
