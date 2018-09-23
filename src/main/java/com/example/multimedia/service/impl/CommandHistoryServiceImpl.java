package com.example.multimedia.service.impl;

import com.example.multimedia.domian.CommandHistory;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.repository.CommandHistoryRepository;
import com.example.multimedia.service.CommandHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author CookiesEason
 * 2018/09/23 13:28
 */
@Service
public class CommandHistoryServiceImpl implements CommandHistoryService {

    @Autowired
    private CommandHistoryRepository commandHistoryRepository;

    @Override
    public PageDTO<CommandHistory> findAll(int page,int size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.Direction.DESC,"createDate");
        Page<CommandHistory> histories = commandHistoryRepository.findAll(pageable);
        return new PageDTO<>(histories.getContent(),histories.getTotalElements(),
                (long)histories.getTotalPages());
    }

    @Override
    public void save(CommandHistory commandHistory) {
        commandHistoryRepository.save(commandHistory);
    }
}
