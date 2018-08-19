package com.example.multimedia.repository;

import com.example.multimedia.domian.maindomian.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/07 13:58
 */
public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {

    CommentLike findByUserIdAndCommentId(Long userId, Long commentId);

    Long countAllBycommentIdAndStatus(Long commentId, boolean status);

    void deleteAllByCommentIdIn(List<Long> ids);

    void deleteAllByCommentId(Long id);

}
