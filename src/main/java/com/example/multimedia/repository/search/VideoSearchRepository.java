package com.example.multimedia.repository.search;

import com.example.multimedia.domian.videodomian.search.VideoSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/12 13:15
 */
public interface VideoSearchRepository extends ElasticsearchRepository<VideoSearch,Long> {
}
