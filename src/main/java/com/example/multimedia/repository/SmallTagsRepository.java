package com.example.multimedia.repository;

import com.example.multimedia.domian.maindomian.tag.SmallTags;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(value = "SELECT count(*) from article_small_tags where small_tags_id = :id",nativeQuery = true)
    Long articeNum(@Param("id") Long id);

    @Query(value = "SELECT count(*) from video_small_tags where small_tags_id = :id",nativeQuery = true)
    Long videoNum(@Param("id") Long id);

    @Query(value = "SELECT sum(like_count) from article_small_tags \n" +
            "INNER JOIN article on article.id = article_id\n" +
            "where small_tags_id = :id",nativeQuery = true)
    Long articelHot(@Param("id") Long id);

    @Query(value = "SELECT sum(like_count) from video_small_tags \n" +
            "INNER JOIN video on video.id = video_id\n" +
            "where small_tags_id = :id",nativeQuery = true)
    Long videoHot(@Param("id") Long id);


    @Query(value = "SELECT COUNT(*) as time,t.small_tag from (\n" +
            "SELECT article_id as id, small_tags_id,small_tags.small_tag from article_small_tags\n" +
            "INNER JOIN small_tags on small_tags.id = small_tags_id\n" +
            "UNION\n" +
            "SELECT video_id as id, small_tags_id,small_tags.small_tag from video_small_tags\n" +
            "INNER JOIN small_tags on small_tags.id = small_tags_id\n" +
            ") t\n" +
            "GROUP BY small_tags_id\n" +
            "ORDER BY time DESC\n" +
            "LIMIT 5 ",nativeQuery = true)
    List<Object[]> topFive();

}
