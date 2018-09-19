package com.example.multimedia.repository.search;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.maindomian.search.UserSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/09/18 11:42
 */
public interface UserSearchRepository extends ElasticsearchRepository<UserSearch,Long> {

    List<UserSearch> findAllByNicknameLike(String nickname);

}
