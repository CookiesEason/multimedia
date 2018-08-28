package com.example.multimedia.repository;

import com.example.multimedia.domian.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CookiesEason
 * 2018/08/28 10:38
 */
public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {

    Page<Announcement> findAllByTag(String tag, Pageable pageable);
}
