package com.example.multimedia.repository;

import com.example.multimedia.domian.maindomian.Article;
import com.example.multimedia.domian.maindomian.tag.SmallTags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * @author CookiesEason
 * 2018/08/18 17:04
 */
public interface ArticleRepository extends JpaRepository<Article,Long> {


    Page<Article> findAllByTagsTag(String tag,Pageable pageable);

    Page<Article> findAllByUserId(Long userId,Pageable pageable);

    void deleteByIdAndUserId(Long id, Long userId);

    List<Article> findAllBySmallTags(SmallTags smallTags);

    Page<Article> findAllBySmallTags(SmallTags smallTags,Pageable pageable);

}
