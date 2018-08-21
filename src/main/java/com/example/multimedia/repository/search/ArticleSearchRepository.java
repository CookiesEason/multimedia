package com.example.multimedia.repository.search;

import com.example.multimedia.domian.maindomian.search.ArticleSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author CookiesEason
 * 2018/08/21 15:18
 */
public interface ArticleSearchRepository extends ElasticsearchRepository<ArticleSearch,Long> {
}
