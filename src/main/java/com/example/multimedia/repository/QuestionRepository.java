package com.example.multimedia.repository;

import com.example.multimedia.domian.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/16 14:12
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "SELECT * FROM question ORDER BY rand() LIMIT 10;",nativeQuery = true)
    List<com.example.multimedia.domian.Question> findByRand();
}
