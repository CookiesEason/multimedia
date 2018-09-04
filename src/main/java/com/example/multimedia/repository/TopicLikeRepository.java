package com.example.multimedia.repository;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.TopicLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/07 14:54
 */
public interface TopicLikeRepository extends JpaRepository<TopicLike, Long> {

    TopicLike findByUserIdAndTopIdAndTopic(Long userId, Long topId, Topic topic);

    Long countByTopIdAndStatus(Long topId, boolean statues);

    void deleteAllByTopId(Long topId);

    void deleteAllByTopIdAndTopic(Long topId,Topic topic);

    @Query(value = "SELECT top_id FROM topic_like WHERE `status`=1 AND topic=:topic AND user_id = :userId AND TO_DAYS(NOW()) - TO_DAYS(date) <=3",nativeQuery = true)
    long[] ids(@Param("topic") String topic,@Param("userId") Long userId);

    @Query(value = "SELECT top_id FROM topic_like WHERE `status`=1 AND topic=:topic AND user_id = :userId AND TO_DAYS(NOW()) - TO_DAYS(date) <=30",nativeQuery = true)
    long[] likeIds(@Param("topic") String topic,@Param("userId") Long userId);
}
