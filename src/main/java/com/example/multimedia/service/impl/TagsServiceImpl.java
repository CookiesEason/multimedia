package com.example.multimedia.service.impl;

import com.example.multimedia.domian.maindomian.Tags;
import com.example.multimedia.repository.TagsRepository;
import com.example.multimedia.service.TagsService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/18 17:22
 */
@Service
public class TagsServiceImpl implements TagsService{

    @Autowired
    private TagsRepository tagsRepository;

    @Override
    @Cacheable(value = "tags")
    public List<Tags> getTags() {
        return tagsRepository.findAll();
    }

    @Override
    @CacheEvict(value = "tags",allEntries = true)
    public ResultVo updateTag(String oldTag,String tag) {
        Tags tags = tagsRepository.findByTag(oldTag);
        tags.setTag(tag);
        tagsRepository.save(tags);
        return ResultVoUtil.success();
    }

    @Override
    @CacheEvict(value = "tags",allEntries = true)
    public ResultVo addTag(String tag) {
        if (tagsRepository.findByTag(tag)!=null){
            return ResultVoUtil.error(0,"标签已存在");
        }
        Tags tags = new Tags();
        tags.setTag(tag);
        tagsRepository.save(tags);
        return ResultVoUtil.success();
    }

    @Override
    @CacheEvict(value = "tags",allEntries = true)
    public ResultVo deleteTag(String tag) {
        tagsRepository.deleteByTag(tag);
        return ResultVoUtil.success();
    }

    @Override
    @Cacheable(value = "tags",key = "#tag")
    public Tags findByTag(String tag) {

        return tagsRepository.findByTag(tag);
    }

    @Override
    public ResultVo hotWorksByTag(String tag) {
        return ResultVoUtil.success(tagsRepository.getHotSimpleWorks(tag));
    }

}
