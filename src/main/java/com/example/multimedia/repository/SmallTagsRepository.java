package com.example.multimedia.repository;

import com.example.multimedia.domian.maindomian.tag.SmallTags;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * @author CookiesEason
 * 2018/08/25 20:33
 */
public interface SmallTagsRepository extends JpaRepository<SmallTags,Long> {

    List<SmallTags> findAllByTagsTag(String tag);

    SmallTags findBySmallTag(String smallTag);

    Set<SmallTags> findBySmallTagIn(Set<String> set);

}
