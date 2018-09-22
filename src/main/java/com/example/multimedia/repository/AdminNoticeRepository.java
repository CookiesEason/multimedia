package com.example.multimedia.repository;

import com.example.multimedia.domian.AdminNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CookiesEason
 * 2018/08/20 20:35
 */
public interface AdminNoticeRepository extends JpaRepository<AdminNotice,Long> {

    Long countAllByReaded(Boolean readed);

    Page<AdminNotice> findAllByType(String type, Pageable pageable);

}
