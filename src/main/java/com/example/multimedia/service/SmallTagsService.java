package com.example.multimedia.service;

import com.example.multimedia.domian.maindomian.tag.SmallTags;
import com.example.multimedia.dto.AdminLableDTO;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.dto.SmallTagDTO;
import com.example.multimedia.vo.ResultVo;

import java.util.List;
import java.util.Set;

/**
 * @author CookiesEason
 * 2018/08/25 20:54
 */
public interface SmallTagsService {

    ResultVo save(String smallTag, String tag);

    List<SmallTagDTO> getSmallTagByTag(String tag);

    List<AdminLableDTO> findAll(int page);

    ResultVo update(Long id,String smallTag);

    ResultVo delete(Long id);

    Set<SmallTags> findAllBySmallTag(Set<String> set);

    SmallTags findBySmallTag(String tag);

    List<Object> topFive();

}
