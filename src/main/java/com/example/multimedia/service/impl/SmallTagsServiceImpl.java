package com.example.multimedia.service.impl;

import com.example.multimedia.domian.maindomian.Article;
import com.example.multimedia.domian.maindomian.Tags;
import com.example.multimedia.domian.maindomian.Video;
import com.example.multimedia.domian.maindomian.tag.SmallTags;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.dto.SmallTagDTO;
import com.example.multimedia.repository.ArticleRepository;
import com.example.multimedia.repository.SmallTagsRepository;
import com.example.multimedia.repository.VideoRepository;
import com.example.multimedia.service.SmallTagsService;
import com.example.multimedia.service.TagsService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author CookiesEason
 * 2018/08/25 20:55
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmallTagsServiceImpl implements SmallTagsService {

    @Autowired
    private SmallTagsRepository smallTagsRepository;
    
    @Autowired
    private TagsService tagsService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    @CacheEvict(value = "smallTags",key = "#tag")
    public ResultVo save(String smallTag, String tag) {
        Tags tags = tagsService.findByTag(tag);
        if (tags==null){
            return ResultVoUtil.error(0,"分类不存在");
        }
        if (findBySmallTag(smallTag)!=null){
            return ResultVoUtil.error(0,"标签已存在");
        }
        SmallTags smallTags = new SmallTags();
        smallTags.setSmallTag(smallTag);
        smallTags.setTags(tags);
        smallTagsRepository.save(smallTags);
        return ResultVoUtil.success();
    }

    @Override
    @Cacheable(value = "smallTags",key = "#tag")
    public List<SmallTagDTO> getSmallTagByTag(String tag) {
        List<SmallTagDTO> smallTagDTOS = new ArrayList<>();
        smallTagsRepository.findAllByTagsTag(tag).forEach(smallTags -> {
            SmallTagDTO smallTagDTO = new SmallTagDTO(smallTags);
            smallTagDTOS.add(smallTagDTO);
        });
        return smallTagDTOS;
    }

    @Override
    @Cacheable(value = "smallTags")
    public PageDTO<SmallTagDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<SmallTags> smallTagsPage = smallTagsRepository.findAll(pageable);
        List<SmallTagDTO> smallTagDTOS = new ArrayList<>();
        smallTagsPage.forEach(smallTag -> {
            SmallTagDTO smallTagDTO = new SmallTagDTO(smallTag,smallTag.getTags().getTag());
            smallTagDTOS.add(smallTagDTO);
        });
        return new PageDTO<>(smallTagDTOS, smallTagsPage.getTotalElements(),
                (long) smallTagsPage.getTotalPages());
    }

    @Override
    @CacheEvict(value = "smallTags",allEntries = true)
    public ResultVo update(Long id,String smallTag) {
        Optional<SmallTags> smallTagsOptional = smallTagsRepository.findById(id);
        if (smallTagsOptional.isPresent()){
            SmallTags smallTags = smallTagsOptional.get();
            smallTags.setSmallTag(smallTag);
            smallTagsRepository.save(smallTags);
            return ResultVoUtil.success();
        }
        return ResultVoUtil.error(0,"发生错误");
    }

    @Override
    @CacheEvict(value = "smallTags",allEntries = true)
    public ResultVo delete(Long id) {
        SmallTags smallTags = smallTagsRepository.getOne(id);
        List<Article> articles = new ArrayList<>();
        articleRepository.findAllBySmallTags(smallTags).forEach(article -> {
            article.getSmallTags().remove(smallTags);
            articles.add(article);
        });
        List<Video> videos = new ArrayList<>();
        videoRepository.findAllBySmallTags(smallTags).forEach(video -> {
            video.getSmallTags().remove(smallTags);
            videos.add(video);
        });
        articleRepository.saveAll(articles);
        videoRepository.saveAll(videos);
        smallTagsRepository.deleteById(id);
        return ResultVoUtil.success();
    }

    @Override
    public Set<SmallTags> findAllBySmallTag(Set<String> set) {
        return smallTagsRepository.findBySmallTagIn(set);
    }

    @Override
    public SmallTags findBySmallTag(String tag) {
        return smallTagsRepository.findBySmallTag(tag);
    }


}
