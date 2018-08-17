package com.example.multimedia.repository;

import com.example.multimedia.domian.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/14 20:19
 */
public interface NoticeRepository extends JpaRepository<Notice,Long> {

    Page<Notice> findAllByToUid(Long toUid, Pageable pageable);

    void  deleteByIdAndToUid(Long id, Long toUid);

    Long countAllByToUidAndReaded(Long toUid, Boolean readed);

}
