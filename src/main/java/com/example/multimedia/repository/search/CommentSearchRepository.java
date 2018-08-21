package com.example.multimedia.repository.search;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.search.CommentSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author CookiesEason
 * 2018/08/12 17:21
 */
public interface CommentSearchRepository extends ElasticsearchRepository<CommentSearch,Long> {
    Page<CommentSearch> findAllByContent(String content, Pageable pageable);

    void deleteAllByTopidAndTopic(Long topicId, Topic topic);

}
