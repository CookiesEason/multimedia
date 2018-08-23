package com.example.multimedia.repository;

import com.example.multimedia.domian.maindomian.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * @author CookiesEason
 * 2018/08/05 20:03
 */
public interface ReplyRepository extends JpaRepository<Reply,Long> {

    List<Reply> findAllByCommentId(Long commentId);

    List<Reply> deleteAllByCommentId(Long id);

    void deleteByIdAndFromUid(Long id,Long fromId);

    List<Reply> deleteAllByCommentIdIn(List <Long> ids);
}
