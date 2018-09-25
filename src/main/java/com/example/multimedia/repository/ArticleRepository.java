package com.example.multimedia.repository;

import com.example.multimedia.domian.maindomian.Article;
import com.example.multimedia.domian.maindomian.tag.SmallTags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author CookiesEason
 * 2018/08/18 17:04
 */
public interface ArticleRepository extends JpaRepository<Article,Long> {

    Optional<Article> findByIdAndEnable(Long id,Boolean enable);

    Page<Article> findAllByEnable(Pageable pageable,Boolean enable);

    Page<Article> findAllByTagsTagAndEnable(String tag,Pageable pageable,Boolean enable);

    Page<Article> findAllByUserIdAndEnable(Long userId,Pageable pageable,Boolean enable);

    void deleteByIdAndUserId(Long id, Long userId);

    List<Article> findAllBySmallTags(SmallTags smallTags);

    Page<Article> findAllBySmallTagsAndEnable(SmallTags smallTags,Pageable pageable,Boolean enable);

    Page<Article> findAllByIdInAndEnable(long[] ids,Pageable pageable,Boolean enable);

    List<Article> findAllByIdIn(long[] ids);

    @Query(value = "SELECT count(*) FROM article WHERE TO_DAYS(NOW()) - TO_DAYS(create_date) <= :day",nativeQuery = true)
    int countArticlesForDays(@Param("day") int day);

    Long countAllByTagsTagAndUserId(String tag,Long userId);

    Long countAllByUserIdAndEnable(Long userId,Boolean enable);

    @Query(value = "SELECT title FROM article WHERE id = :id",nativeQuery = true)
    String articleTitle(@Param("id") Long id);

}
