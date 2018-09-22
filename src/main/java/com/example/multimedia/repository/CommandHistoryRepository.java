package com.example.multimedia.repository;

import com.example.multimedia.domian.CommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CookiesEason
 * 2018/09/22 18:26
 */
public interface CommandHistoryRepository extends JpaRepository<CommandHistory,Long> {
}
