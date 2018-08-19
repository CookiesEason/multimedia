package com.example.multimedia.repository;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.TopicLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CookiesEason
 * 2018/08/07 14:54
 */
public interface TopicLikeRepository extends JpaRepository<TopicLike, Long> {

    TopicLike findByUserIdAndTopIdAndTopic(Long userId, Long topId, Topic topic);

    Long countByTopIdAndStatus(Long topId, boolean statues);

    void deleteAllByTopId(Long topId);

    void deleteAllByTopIdAndTopic(Long topId,Topic topic);
}
