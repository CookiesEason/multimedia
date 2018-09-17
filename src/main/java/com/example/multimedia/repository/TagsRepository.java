package com.example.multimedia.repository;

import com.example.multimedia.domian.maindomian.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/03 18:20
 */
public interface TagsRepository extends JpaRepository<Tags,Long> {
    Tags findByTag(String tag);

    void deleteByTag(String tag);

    @Query(value = "SELECT t.id,title,type,img_url from (\n" +
            "SELECT id,title,like_count,tags_id,'article' as type,bg_img as img_url from article WHERE enable=TRUE \n" +
            "UNION\n" +
            "SELECT id,title,like_count,tags_id,'video' as type,img_url from video where enable=true and auditing=true \n" +
            ") t INNER JOIN tags ON t.tags_id = tags.id\n" +
            "WHERE tag = :tag\n" +
            "ORDER BY like_count desc\n" +
            "LIMIT 10",nativeQuery = true)
    List<Object[]> getHotSimpleWorks(@Param("tag") String tag);
}
