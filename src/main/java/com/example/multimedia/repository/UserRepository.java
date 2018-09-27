package com.example.multimedia.repository;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserInfo;
import com.example.multimedia.domian.UserRole;
import com.example.multimedia.dto.SimpleWorkDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/07/23 15:19
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findUserById(Long id);

    User findByUsername(String username);

    User findByUserInfoNickname(String nickname);

    Page<User> findAllByRoleListIn(Pageable pageable, List<UserRole> userRoles);

    List<User> findByUsernameOrUserInfoNickname(String username,String nickname);

    List<User> findByUsernameLikeOrUserInfoNicknameLike(String username,String nickname);

    List<User> findUsersByIdIn(List<Long> ids);

    @Query(value = "SELECT nickname FROM `user` INNER JOIN user_info ON user_info_id = user_info.id WHERE `user`.id = :Id",
    nativeQuery = true)
    String nickname(@Param("Id") Long userId);

    @Query(value = "SELECT SUM(like_count) FROM (select SUM(like_count) as like_count from video where user_id = :id\n" +
            "UNION\n" +
            "select SUM(like_count)as like_count from article where user_id = :id)t",nativeQuery = true)
    Long getUserWorkHot(@Param("id") Long userId);

    @Query(value = "SELECT * FROM (SELECT t.user_id,SUM(num) as hot from (SELECT user_id ,SUM(like_count) as num from `user` INNER JOIN article ON article.user_id = `user`.id\n" +
            "GROUP BY `user`.id\n" +
            "UNION ALL\n" +
            "SELECT user_id,SUM(like_count) as num from `user` INNER JOIN video ON video.user_id = `user`.id\n" +
            "GROUP BY `user`.id) t\n" +
            "GROUP BY t.user_id\n" +
            "ORDER BY SUM(t.num)\n" +
            ")ids INNER JOIN `user` ON ids.user_id = `user`.id order by hot DESC ",
            countQuery = "select count(*) from user"
            ,nativeQuery = true)
    Page<User> getHotUsers(Pageable pageable);


    @Query(value = "select COUNT(*) as now ,(select COUNT(*) as last from `user` where period_diff( date_format(now() , '%y%m') ,date_format( `user`.date, '%y%m' ) ) =1) as last\n" +
            "from `user` where date_format(`user`.date, '%y%m' ) = date_format( curdate() , '%y%m' )",nativeQuery = true)
    List<Object> countNewRegister();

   @Query(value = "SELECT * FROM (SELECT video.user_id,date FROM topic_like  INNER JOIN\n" +
           "video on video.id = topic_like.top_id\n" +
           "WHERE `status`=1 AND topic='VIDEO' AND topic_like.user_id =:userId  AND TO_DAYS(NOW()) - TO_DAYS(date) <=3\n" +
           "UNION\n" +
           "SELECT article.user_id,date FROM topic_like  INNER JOIN\n" +
           "article on article.id = topic_like.top_id\n" +
           "WHERE `status`=1 AND topic='ARTICLE' AND topic_like.user_id =:userId  AND TO_DAYS(NOW()) - TO_DAYS(date) <=3)\n" +
           "t INNER JOIN `user` ON t.user_id = user.id GROUP BY user_id ORDER BY t.date desc \n",
           countQuery = "select count(*) from user"
           ,nativeQuery = true)
   Page<User> likeUserIds(@Param("userId")Long userId, Pageable pageable);

   @Query(value = "SELECT id,title,type from (\n" +
           "SELECT id,title,create_date,'article' as type from article WHERE user_id = :id\n" +
           "UNION\n" +
           "SELECT id,title,create_date,'video' as type from video WHERE user_id = :id\n" +
           ") t\n" +
           "ORDER BY create_date desc\n" +
           "LIMIT 3 ",nativeQuery = true)
   List<Object[]> getSimpleWorks(@Param("id") Long id);
}
