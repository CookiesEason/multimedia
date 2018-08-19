package com.example.multimedia.repository.search;

import com.example.multimedia.domian.maindomian.search.VideoSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author CookiesEason
 * 2018/08/12 13:15
 */
public interface VideoSearchRepository extends ElasticsearchRepository<VideoSearch,Long> {
}
