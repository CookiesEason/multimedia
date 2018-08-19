package com.example.multimedia.repository;

import com.example.multimedia.domian.maindomian.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/07 12:54
 */
public interface ReplyLikeRepository extends JpaRepository<ReplyLike,Long> {

    ReplyLike findByUserIdAndReplyId(Long userId, Long replyId);

    Long countAllByReplyIdAndStatus(Long id, boolean status);

    void deleteAllByReplyIdIn(List<Long> ids);

    void deleteAllByReplyId(Long id);

}
