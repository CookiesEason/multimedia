package com.example.multimedia.service.impl.mainserviceimpl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.Article;
import com.example.multimedia.domian.maindomian.Tags;
import com.example.multimedia.domian.maindomian.TopicLike;
import com.example.multimedia.domian.maindomian.tag.SmallTags;
import com.example.multimedia.dto.ArticleDTO;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.dto.SimpleUserDTO;
import com.example.multimedia.dto.SmallTagDTO;
import com.example.multimedia.repository.ArticleRepository;
import com.example.multimedia.service.*;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author CookiesEason
 * 2018/08/18 17:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    @Qualifier(value = "LikeService")
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private SmallTagsService smallTagsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminNoticeService adminNoticeService;

    @Autowired
    private FileService fileService;

    @Override
    public ResultVo save(String title, String text, String tag, MultipartFile multipartFile, Set<String> smallTags) {
        Article article = new Article();
        article.setTitle(title);
        article.setText(text);
        Tags tags = tagsService.findByTag(tag);
        Set<SmallTags> smallTagsSet = smallTagsService.findAllBySmallTag(smallTags);
        if (tags!=null){
            if (smallTagsSet.size()>0){
                article.setUserId(getUid());
                article.setTags(tags);
                article.setBgImg(fileService.uploadFile(multipartFile).getData().toString());
                article.setSmallTags(smallTagsSet);
                articleRepository.save(article);
                return ResultVoUtil.success();
            }
            return ResultVoUtil.error(0,"必须选择一个标签");
        }
        return ResultVoUtil.error(0,"分类不存在,请检查你选择的分类");
    }

    @Override
    public ResultVo update(Long articleId, String title, String text, MultipartFile multipartFile,
                           String tag,Set<String> smallTags) {
        Optional<Article> article = articleRepository.findById(articleId);
        if (article.isPresent()){
            Article newArticle = article.get();
            newArticle.setTitle(title);
            newArticle.setText(text);
            if (multipartFile!=null){
                newArticle.setBgImg(fileService.uploadFile(multipartFile).getData().toString());
            }
            Tags tags = tagsService.findByTag(tag);
            Set<SmallTags> smallTagsSet = smallTagsService.findAllBySmallTag(smallTags);
            if (tags!=null){
                if (smallTagsSet.size()>0){
                    newArticle.setTags(tags);
                    newArticle.setSmallTags(smallTagsSet);
                    articleRepository.save(newArticle);
                    return ResultVoUtil.success();
                }
                return ResultVoUtil.error(0,"必须选择一个标签");
            }
            return ResultVoUtil.error(0,"分类不存在,请检查你选择的分类");
        }
        return ResultVoUtil.error(0,"发生未知的错误，请刷新页面重试");
    }

    @Override
    public ResultVo deleteById(Long articleId) {
        articleRepository.deleteByIdAndUserId(articleId,getUid());
        commentService.deleteAllBycontentId(articleId, Topic.ARTICLE);
        likeService.deleteAllById(articleId,Topic.ARTICLE);
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo findAll(int page,int size,String order,String sort) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<Article> articlePage =  articleRepository.findAll(pageable);
        return getResultVo(articlePage);
    }

    @Override
    public ResultVo findAllByTag(int page, int size, String order, String sort,String tag) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<Article> articlePage = articleRepository.findAllByTagsTag(tag,pageable);
        return getResultVo(articlePage);
    }

    @Override
    public ResultVo findMyAll(int page, int size, String order, String sort) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<Article> articlePage = articleRepository.findAllByUserId(getUid(),pageable);
        return getResultVo(articlePage);
    }

    @Override
    public ResultVo findUserAll(Long userId,int page, int size, String order, String sort) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<Article> articlePage = articleRepository.findAllByUserId(userId,pageable);
        return getResultVo(articlePage);
    }

    @Override
    public ResultVo findById(Long id) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        if (articleOptional.isPresent()){

            boolean isLike = false;
            Long userId = getUid();
            TopicLike topicLike = (TopicLike) likeService.status(id,userId,Topic.ARTICLE);
            if (topicLike !=null){
                isLike = topicLike.isStatus();
            }
            Article article = articleOptional.get();
            article.setReadCount(article.getReadCount()+1);
            save(article);
            Set<SmallTagDTO> smallTagDTOS = new HashSet<>();
            article.getSmallTags().forEach(smallTags -> {
                SmallTagDTO smallTagDTO = new SmallTagDTO(smallTags);
                smallTagDTOS.add(smallTagDTO);
            });
            return ResultVoUtil.success(new ArticleDTO(new SimpleUserDTO(userService.findById(article.getUserId())),
                    article,isLike,smallTagDTOS));
        }
        return ResultVoUtil.error(404,"发生未知的错误");
    }

    @Override
    public Article findById(long id) {
        Optional<Article> video = articleRepository.findById(id);
        return video.orElse(null);
    }

    @Override
    public ResultVo findAllBySmallTag(int page, int size,String smallTag,String sort) {
        Sort s = new Sort(Sort.Direction.DESC,sort);
        Pageable pageable = PageRequest.of(page,size,s);
        Page<Article> articlePage = articleRepository.findAllBySmallTags(smallTagsService.findBySmallTag(smallTag),pageable);
        return getResultVo(articlePage);
    }

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

    @Override
    public ResultVo reportArticle(Long articleId, String reason, String content) {
        Article article = findById((long)articleId);
        if (article== null){
            return ResultVoUtil.error(0,"发生错误");
        }
         adminNoticeService.save(articleId,Topic.ARTICLE,article.getTitle(),
                 reason,content,"report");
         return ResultVoUtil.success();
    }

    @Override
    public int countArticlesForDays(int day) {
        return articleRepository.countArticlesForDays(day);
    }

    private ResultVo getResultVo(Page<Article> articlePage) {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        Set<SmallTagDTO> smallTagDTOS = new HashSet<>();
        articlePage.getContent().forEach(article -> {
            article.getSmallTags().forEach(smallTags -> {
                SmallTagDTO smallTagDTO = new SmallTagDTO(smallTags);
                smallTagDTOS.add(smallTagDTO);
            });
            User user = userService.findById(article.getUserId());
            ArticleDTO articleDTO = new ArticleDTO(new SimpleUserDTO(user.getId(),user.getUserInfo().getNickname(),
                    user.getUserInfo().getHeadImgUrl()),
                    article,smallTagDTOS);
            articleDTOList.add(articleDTO);
        });
        return ResultVoUtil.success(new PageDTO<>(articleDTOList, articlePage.getTotalElements(),
                (long) articlePage.getTotalPages()));
    }

    private Sort sort(String order, String sort){
        Sort st;
        if ("asc".equals(order)){
            st = new Sort(Sort.Direction.ASC,sort);
        }else {
            st = new Sort(Sort.Direction.DESC,sort);
        }
        return st;
    }

    private Long getUid(){
        User user = userService.findByUsername(UserUtil.getUserName());
        if (user!=null){
            return user.getId();
        }
        return null;
    }
}
