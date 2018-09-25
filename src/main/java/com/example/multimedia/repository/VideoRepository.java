package com.example.multimedia.repository;

import com.example.multimedia.domian.maindomian.Video;
import com.example.multimedia.domian.maindomian.tag.SmallTags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * @author CookiesEason
 * 2018/08/03 16:24
 */
public interface VideoRepository extends JpaRepository<Video,Long> {

    Video findByIdAndAuditingAndEnable(long id,boolean enable, boolean auditing);

    void deleteByIdAndUserId(long id,long userId);

    Video findByIdAndUserId(long id,long userId);

    Page<Video> findAllByUserIdAndEnableAndAuditing(Pageable pageable, Long userId,boolean enable,boolean auditing);

    Page<Video> findAllByEnableAndAuditing(Pageable pageable, boolean enable, boolean auditing);

    Page<Video> findAllByEnableAndTagsTagAndAuditing(Pageable pageable,boolean enable,String tag,boolean auditing);

    List<Video> findAllBySmallTags(SmallTags smallTags);

    Page<Video> findAllBySmallTagsAndEnableAndAuditing(SmallTags smallTags, Pageable pageable,boolean enable, boolean auditing);

    Page<Video> findAllByIdInAndEnableAndAuditing(long[] ids,Pageable pageable,boolean enable, boolean auditing);

    List<Video> findAllByIdIn(long[] ids);

    @Query(value = "SELECT count(*) FROM video WHERE TO_DAYS(NOW()) - TO_DAYS(create_date) <= :day",nativeQuery = true)
    int countVideosForDays(@Param("day") int day);

    Long countAllByTagsTagAndUserIdAndEnableAndAuditing(String tag,Long userId,boolean enable, boolean auditing);

    Long countAllByCreateDateAfterAndEnableAndAuditing(Date date,boolean enable, boolean auditing);

    Long countAllByTagsTagAndEnableAndAuditing(String tag,boolean enable, boolean auditing);

    Long countAllByUserIdAndEnable(Long userId,Boolean enable);

    @Query(value = "SELECT title FROM video WHERE id = :id",nativeQuery = true)
    String videoTitle(@Param("id") Long id);

    @Query(value = "select COUNT(*)+(select COUNT(*) from `article` where date_format( `article`.create_date, '%y%m' ) = date_format(curdate() , '%y%m') ) as now\n" +
            "from `video` where date_format( `video`.create_date, '%y%m' ) = date_format(curdate() , '%y%m')\n" +
            "UNION\n" +
            "select COUNT(*)+(select COUNT(*) as last from `article` where period_diff( date_format(now() , '%y%m') ,date_format( `article`.create_date, '%y%m' ) ) =1) as last\n" +
            "from `video` where period_diff( date_format(now() , '%y%m') ,date_format( `video`.create_date, '%y%m' ) ) =1",
    nativeQuery = true)
    List<Object> works();
}
