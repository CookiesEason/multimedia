package com.example.multimedia.repository;

import com.example.multimedia.domian.maindomian.Comment;
import com.example.multimedia.domian.enums.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/05 19:38
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

    Comment findCommentById(Long id);

    Page<Comment> findAllByTopIdAndTopic(Pageable pageable, Long videoId, Topic topic);

    Long  deleteByIdAndFromUid(Long id,Long fromUid);

    List<Comment> deleteAllByTopIdAndTopic(Long video,Topic topic);

    List<Comment> findAllByTopIdAndTopicAndFromUid(Long topicId,Topic topic,Long fromUid);

    Long countAllByTopicAndTopId(Topic topic,Long id);

}
