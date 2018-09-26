package com.example.multimedia.repository.search;

import com.example.multimedia.domian.maindomian.search.ArticleSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author CookiesEason
 * 2018/08/21 15:18
 */
public interface ArticleSearchRepository extends ElasticsearchRepository<ArticleSearch,Long> {

    Page<ArticleSearch> findAllByTagAndTitleOrTextAndEnable(Pageable pageable,String tag,String content,String text,Boolean enable);

    Page<ArticleSearch> findAllByTitleOrTextAndEnable(Pageable pageable,String content,String text,Boolean enable);

}
