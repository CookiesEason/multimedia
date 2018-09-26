package com.example.multimedia.repository;

import com.example.multimedia.domian.Problem;
import com.example.multimedia.domian.enums.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CookiesEason
 * 2018/09/10 19:13
 */
public interface ProblemRepository extends JpaRepository<Problem,Long> {

    Problem findByTopicIdAndTopic(Long id, Topic topic);

    void deleteByTopicIdAndTopic(Long topicId,Topic topic);

}
