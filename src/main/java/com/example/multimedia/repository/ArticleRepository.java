package com.example.multimedia.repository;

import com.example.multimedia.domian.maindomian.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CookiesEason
 * 2018/08/18 17:04
 */
public interface ArticleRepository extends JpaRepository<Article,Long> {


    Page<Article> findAllByEnableAndTagsTag(Boolean enable,String tag,Pageable pageable);

    Page<Article> findAllByEnable(Boolean enable, Pageable pageable);

    Page<Article> findAllByUserIdAndEnable(Long userId,Boolean enable,Pageable pageable);

    void deleteByIdAndUserId(Long id, Long userId);

}
