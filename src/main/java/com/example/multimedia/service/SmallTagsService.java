package com.example.multimedia.service;

import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.dto.SmallTagDTO;
import com.example.multimedia.vo.ResultVo;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/25 20:54
 */
public interface SmallTagsService {

    ResultVo save(String smallTag, String tag);

    List<SmallTagDTO> getSmallTagByTag(String tag);

    PageDTO<SmallTagDTO> findAll(int page);

    ResultVo update(Long id,String smallTag);

    ResultVo delete(Long id);

}
