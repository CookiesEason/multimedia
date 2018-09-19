package com.example.multimedia.repository.search;

import com.example.multimedia.domian.maindomian.search.VideoSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author CookiesEason
 * 2018/08/12 13:15
 */
public interface VideoSearchRepository extends ElasticsearchRepository<VideoSearch,Long> {

    Page<VideoSearch> findAllByTimeBetweenAndTagAndTitle(Pageable pageable,int startTime,int endTime,
                                                             String tag,String title);
    Page<VideoSearch> findAllByTimeBetweenAndTitle(Pageable pageable,int startTime,int endTime,String title);
}
