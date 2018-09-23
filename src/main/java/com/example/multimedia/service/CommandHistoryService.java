package com.example.multimedia.service;

import com.example.multimedia.domian.CommandHistory;
import com.example.multimedia.dto.PageDTO;

/**
 * @author CookiesEason
 * 2018/09/23 13:27
 */
public interface CommandHistoryService {

    PageDTO<CommandHistory> findAll(int page, int size);

    void save(CommandHistory commandHistory);

}
