package com.example.multimedia.repository;

import com.example.multimedia.domian.maindomian.Video;
import com.example.multimedia.domian.maindomian.tag.SmallTags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * @author CookiesEason
 * 2018/08/03 16:24
 */
public interface VideoRepository extends JpaRepository<Video,Long> {

    Video findById(long id);

    void deleteByIdAndUserId(long id,long userId);

    Video findByIdAndUserId(long id,long userId);

    Page<Video> findAllByUserIdAndEnable(Pageable pageable, Long userId,boolean enable);

    Page<Video> findAllByEnable(Pageable pageable, boolean enable);

    Page<Video> findAllByEnableAndTagsTag(Pageable pageable,boolean enable,String tag);

    List<Video> findAllBySmallTags(SmallTags smallTags);

    Page<Video> findAllBySmallTags(SmallTags smallTags, Pageable pageable);

    Page<Video> findAllByIdIn(long[] ids,Pageable pageable);

    List<Video> findAllByIdIn(long[] ids);

    @Query(value = "SELECT count(*) FROM video WHERE TO_DAYS(NOW()) - TO_DAYS(create_date) <= :day",nativeQuery = true)
    int countVideosForDays(@Param("day") int day);

    Long countAllByTagsTagAndUserId(String tag,Long userId);

}
