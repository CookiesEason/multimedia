package com.example.multimedia.repository.search;

import com.example.multimedia.domian.videodomian.search.VideoReplySearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author CookiesEason
 * 2018/08/12 19:12
 */
public interface VideoReplySearchRepository extends ElasticsearchRepository<VideoReplySearch,Long> {

    Page<VideoReplySearch> findAllByContent(String content, Pageable pageable);
}
