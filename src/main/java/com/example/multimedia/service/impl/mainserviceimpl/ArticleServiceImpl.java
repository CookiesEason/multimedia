package com.example.multimedia.service.impl.mainserviceimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.multimedia.domian.CommandHistory;
import com.example.multimedia.domian.User;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.Article;
import com.example.multimedia.domian.maindomian.Tags;
import com.example.multimedia.domian.maindomian.TopicLike;
import com.example.multimedia.domian.maindomian.tag.SmallTags;
import com.example.multimedia.dto.*;
import com.example.multimedia.repository.ArticleRepository;
import com.example.multimedia.repository.TopicLikeRepository;
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

    @Autowired
    private TopicLikeRepository topicLikeRepository;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private FollowerService followerService;

    @Autowired
    private CommandHistoryService commandHistoryService;

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
    public ResultVo deleteByAdmin(Long articleId) {
        String title = articleRepository.articleTitle(articleId);
        articleRepository.deleteById(articleId);
        commentService.deleteAllBycontentId(articleId, Topic.ARTICLE);
        likeService.deleteAllById(articleId,Topic.ARTICLE);
        CommandHistory commandHistory = new CommandHistory();
        commandHistory.setContent("删除了图文:"+title);
        commandHistory.setCommand("删除");
        commandHistory.setType("图文");
        User u = userService.findByUsername(UserUtil.getUserName());
        commandHistory.setPeople(u.getRoleList().get(0).getRole());
        commandHistoryService.save(commandHistory);
        return ResultVoUtil.success();
    }

    @Override
    public PageDTO<ArticleDTO> findAll(int page,int size,String order,String sort,Boolean enable) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<Article> articlePage =  articleRepository.findAllByEnable(pageable,enable);
        return getResultVo(articlePage);
    }

    @Override
    public PageDTO<ArticleDTO> findAllByTag(int page, int size, String order, String sort,String tag) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<Article> articlePage = articleRepository.findAllByTagsTagAndEnable(tag,pageable,true);
        return getResultVo(articlePage);
    }

    @Override
    public PageDTO<ArticleDTO> findMyAll(int page, int size, String order, String sort,Boolean enable) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<Article> articlePage = articleRepository.findAllByUserIdAndEnable(getUid(),pageable,enable);
        return getResultVo(articlePage);
    }

    @Override
    public PageDTO<ArticleDTO> findUserAll(Long userId,int page, int size, String order, String sort) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<Article> articlePage = articleRepository.findAllByUserIdAndEnable(userId,pageable,true);
        return getResultVo(articlePage);
    }

    @Override
    public ResultVo findById(Long id,Boolean enable) {
        Optional<Article> articleOptional = articleRepository.findByIdAndEnable(id,enable);
        if (articleOptional.isPresent()){
            boolean isLike = false;
            Long userId = getUid();
            TopicLike topicLike = (TopicLike) likeService.status(id,userId,Topic.ARTICLE);
            if (topicLike !=null){
                isLike = topicLike.isStatus();
            }
            Article article = articleOptional.get();
            article.setReadCount(article.getReadCount()+1);
            saveArticle(article);
            Set<SmallTagDTO> smallTagDTOS = new HashSet<>();
            article.getSmallTags().forEach(smallTags -> {
                SmallTagDTO smallTagDTO = new SmallTagDTO(smallTags);
                smallTagDTOS.add(smallTagDTO);
            });
            Sort sort = Sort.by(Sort.Direction.DESC,"createDate");
            Pageable pageable = PageRequest.of(0,5,sort);
            ArticleWorkDTO articleWorkDTO = new ArticleWorkDTO();
            List<SimpleArticleDTO> recommendArticles = new ArrayList<>();
            List<SimpleArticleDTO> hotArticles = new ArrayList<>();
            ArticleDTO articleDTO = new ArticleDTO(new SimpleUserDTO(userService.findById(article.getUserId()),
                    followerService.checkFollow(article.getUserId())),
                    article,isLike,smallTagDTOS,commentService.num(article.getId(),Topic.ARTICLE));
            articleWorkDTO.setArticle(articleDTO);

            Page<Article> articlePage = articleRepository.findAllByTagsTagAndEnable(article.getTags().getTag(),pageable,true);
            articlePage.getContent().forEach(recommendArticle -> {
                SimpleArticleDTO simpleArticleDTO = new SimpleArticleDTO(recommendArticle);
                recommendArticles.add(simpleArticleDTO);
            });
            articleWorkDTO.setRecommendArticles(recommendArticles);

            sort = Sort.by(Sort.Direction.DESC,"likeCount");
            pageable = PageRequest.of(0,5,sort);
            articlePage = articleRepository.findAllByEnable(pageable,true);
            articlePage.forEach(hotArticle -> {
                SimpleArticleDTO simpleArticleDTO = new SimpleArticleDTO(hotArticle);
                hotArticles.add(simpleArticleDTO);
            });
            articleWorkDTO.setHotArticles(hotArticles);

            return ResultVoUtil.success(articleWorkDTO);
        }
        return ResultVoUtil.error(404,"发生未知的错误");
    }

    @Override
    public Article findById(long id) {
        Optional<Article> video = articleRepository.findById(id);
        return video.orElse(null);
    }

    @Override
    public PageDTO<ArticleDTO> findAllBySmallTag(int page, int size,String smallTag,String sort) {
        Sort s = new Sort(Sort.Direction.DESC,sort);
        Pageable pageable = PageRequest.of(page,size,s);
        Page<Article> articlePage = articleRepository.findAllBySmallTagsAndEnable(smallTagsService.findBySmallTag(smallTag),
                pageable,true);
        return getResultVo(articlePage);
    }

    @Override
    public void saveArticle(Article article) {
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

    @Override
    public PageDTO<ArticleDTO> findAllByLike(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articlePage = articleRepository.findAllByIdInAndEnable(topicLikeRepository.ids(Topic.ARTICLE.toString(),
                userId),pageable,true);
        return getResultVo(articlePage);
    }

    @Override
    public ResultVo countWorksProportion(Long userId) {
        List<Tags> tags =  tagsService.getTags();
        JSONArray jsonArray=new JSONArray();
        for (Tags t: tags) {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",t.getTag());
            jsonObject.put("value",articleRepository.countAllByTagsTagAndUserId(t.getTag(),userId));
            jsonArray.add(jsonObject);
        }
        return ResultVoUtil.success(jsonArray);
    }

    @Override
    public ResultVo enable(Long id,String reasons) {
        Article article = findById((long)id);
        article.setEnable(!article.getEnable());
        saveArticle(article);
        CommandHistory commandHistory = new CommandHistory();
        if (article.getEnable()){
            problemService.delete(id,Topic.ARTICLE);
            commandHistory.setContent("上架了图文:"+article.getTitle());
            commandHistory.setCommand("上架");
            commandHistory.setType("图文");
        }
        if (!article.getEnable()){
            problemService.save(id,reasons,Topic.ARTICLE);
            commandHistory.setContent("下架了图文:"+article.getTitle());
            commandHistory.setCommand("下架");
            commandHistory.setType("图文");
        }
        User u = userService.findByUsername(UserUtil.getUserName());
        commandHistory.setPeople(u.getRoleList().get(0).getRole());
        commandHistoryService.save(commandHistory);
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo problems(Long id) {
        return problemService.getById(id,Topic.ARTICLE);
    }

    private PageDTO<ArticleDTO> getResultVo(Page<Article> articlePage) {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        articlePage.getContent().forEach(article -> {
            boolean isLike = false;
            Long userId = getUid();
            TopicLike topicLike = (TopicLike) likeService.status(article.getId(),userId,Topic.ARTICLE);
            if (topicLike !=null){
                isLike = topicLike.isStatus();
            }
            Set<SmallTagDTO> smallTagDTOS = new HashSet<>();
            article.getSmallTags().forEach(smallTags -> {
                SmallTagDTO smallTagDTO = new SmallTagDTO(smallTags);
                smallTagDTOS.add(smallTagDTO);
            });
            User user = userService.findById(article.getUserId());
            ArticleDTO articleDTO = new ArticleDTO(new SimpleUserDTO(user.getId(),user.getUserInfo().getNickname(),
                    user.getUserInfo().getHeadImgUrl()),
                    article,isLike,smallTagDTOS,commentService.num(article.getId(),Topic.ARTICLE));
            articleDTOList.add(articleDTO);
        });
        return new PageDTO<>(articleDTOList, articlePage.getTotalElements(),
                (long) articlePage.getTotalPages());
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
