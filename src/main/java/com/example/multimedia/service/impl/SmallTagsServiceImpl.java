package com.example.multimedia.service.impl;

import com.example.multimedia.domian.maindomian.Article;
import com.example.multimedia.domian.maindomian.Tags;
import com.example.multimedia.domian.maindomian.Video;
import com.example.multimedia.domian.maindomian.tag.SmallTags;
import com.example.multimedia.dto.AdminLableDTO;
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
    @CacheEvict(value = "smallTags",allEntries = true)
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
    public List<AdminLableDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page-1,100);
        Page<SmallTags> smallTagsPage = smallTagsRepository.findAll(pageable);
        List<AdminLableDTO> adminLableDTOList = new ArrayList<>();
        smallTagsPage.getContent().forEach(smallTags -> {
            AdminLableDTO adminLableDTO = new AdminLableDTO();
            adminLableDTO.setId(smallTags.getId());
            adminLableDTO.setSmallTag(smallTags.getSmallTag());
            adminLableDTO.setTag(smallTags.getTags().getTag());
            adminLableDTO.setArticleNum(smallTagsRepository.articeNum(smallTags.getId()));
            adminLableDTO.setVideoNum(smallTagsRepository.videoNum(smallTags.getId()));
            adminLableDTO.setCreateDate(smallTags.getCreateDate());
            Long articleHot = smallTagsRepository.articelHot(smallTags.getId());
            Long videoHot = smallTagsRepository.videoHot(smallTags.getId());
            if (articleHot==null){
                articleHot = 0L;
            }
            if (videoHot==null){
                videoHot = 0L;
            }
            adminLableDTO.setHot(articleHot+videoHot);
            adminLableDTOList.add(adminLableDTO);
        });
        return adminLableDTOList;
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
