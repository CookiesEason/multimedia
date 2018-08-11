package com.example.multimedia.repository;

import com.example.multimedia.domian.videodomian.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CookiesEason
 * 2018/08/03 18:20
 */
public interface TagsRepository extends JpaRepository<Tags,Long> {
    Tags findByTag(String tag);

    void deleteByTag(String tag);
}
