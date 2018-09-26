package com.example.multimedia.service.impl.mainserviceimpl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.Article;
import com.example.multimedia.domian.maindomian.Video;
import com.example.multimedia.domian.maindomian.Comment;
import com.example.multimedia.domian.maindomian.Reply;
import com.example.multimedia.domian.maindomian.search.*;
import com.example.multimedia.dto.*;
import com.example.multimedia.repository.TagsRepository;
import com.example.multimedia.repository.UserRepository;
import com.example.multimedia.repository.search.*;
import com.example.multimedia.service.*;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.elasticsearch.common.lucene.search.function.FiltersFunctionScoreQuery;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author CookiesEason
 * 2018/08/12 15:44
 */
@Service
public class SearchServiceImpl implements SearchService {

    String SCORE_MODE_SUM = "sum";
    int PAGE_SIZE = 10;
    Float  MIN_SCORE = 10.0F;

    @Autowired
    private VideoSearchRepository videoSearchRepository;

    @Autowired
    private ArticleSearchRepository articleSearchRepository;

    @Autowired
    private CommentSearchRepository commentSearchRepository;

    @Autowired
    private ReplySearchRepository replySearchRepository;

    @Autowired
    @Qualifier(value = "ReplyLikeService")
    private LikeService videoReplyLikeService;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSearchRepository userSearchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowerService followerService;

    @Override
    public ResultVo searchUser(String searchContent) {
        List<UserSearch> userList = userSearchRepository.findAllByNicknameLike(searchContent);
        List<SimpleUserWorkDTO> simpleUserWorkDTOS = new ArrayList<>();
        userList.forEach(userSearch -> {
            User user = userService.findById(userSearch.getId());
            Boolean isLike = followerService.checkFollow(user.getId());
            SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user,isLike);
            SimpleUserWorkDTO simpleUserWorkDTO = new SimpleUserWorkDTO();
            List<Object[]> simpleWorkDTOS = userRepository.getSimpleWorks(simpleUserDTO.getId());
            simpleUserWorkDTO.setSimpleUserDTO(simpleUserDTO);
            simpleUserWorkDTO.setSimpleWorkDTOs(simpleWorkDTOS);
            simpleUserWorkDTOS.add(simpleUserWorkDTO);
        });
        return ResultVoUtil.success(simpleUserWorkDTOS);
    }

    @Override
    public VideosDTO searchVideo(int page,String order,String sort,int startTime,int endTime,String tag,
                                String searchContent,Boolean enable,Boolean auditing) {
        SearchQuery searchQuery = getVideoSearchQuery(page,PAGE_SIZE,order,sort,searchContent);
        Sort s = Sort.by(Sort.Direction.DESC,sort);
        Pageable pageable = PageRequest.of(page,10,s);
        Page<VideoSearch> videoSearchPage;
        if (tag!=null){
            videoSearchPage =  videoSearchRepository
                    .findAllByTimeBetweenAndTagAndTitle(pageable,startTime,endTime,tag,searchContent);
        }else {
            videoSearchPage = videoSearchRepository.findAllByTimeBetweenAndTitle(pageable,startTime,endTime,
                    searchContent);
        }
        List<VideoDTO> videoDTOS = new ArrayList<>();
        videoSearchPage.getContent().forEach(videoSearch -> {
            if (enable.equals(videoSearch.getEnable())&&auditing.equals(videoSearch.getAuditing())){
                Video video = new Video(videoSearch,tagsRepository.findByTag(videoSearch.getTag()));
                User user = userService.findById(video.getUserId());
                VideoDTO videoDTO = new VideoDTO(new SimpleUserDTO(user.getId(),user.getUserInfo().getNickname(),
                        user.getUserInfo().getHeadImgUrl()),
                        video,null,videoSearch.getComment_num());
                videoDTOS.add(videoDTO);
            }
        });
        VideosDTO videosDTO = new VideosDTO(videoDTOS,videoSearchPage.getTotalElements(),
                (long) videoSearchPage.getTotalPages());
        return videosDTO;
    }

    @Override
    public PageDTO<ArticleDTO> searchArticle(int page, String order, String sort, String searchContent,String tag,Boolean enable) {
        //SearchQuery searchQuery = getArticleSearchQuery(page,PAGE_SIZE,order,sort,searchContent);
        Page<ArticleSearch> articleSearchPage ;
        Sort s = Sort.by(Sort.Direction.DESC,sort);
        Pageable pageable = PageRequest.of(page,10,s);
        if (tag!=null){
            articleSearchPage = articleSearchRepository.findAllByTagAndTitleOrTextAndEnable(pageable,
                    tag, searchContent,searchContent,enable);
        }else {
            articleSearchPage = articleSearchRepository.findAllByTitleOrTextAndEnable(pageable,searchContent,searchContent,enable);
        }
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        articleSearchPage.getContent().forEach(articleSearch -> {
            Article article = new Article(articleSearch,tagsRepository.findByTag(articleSearch.getTag()));
            User user = userService.findById(article.getUserId());
            ArticleDTO articleDTO = new ArticleDTO(new SimpleUserDTO(user.getId(),user.getUserInfo().getNickname(),
                    user.getUserInfo().getHeadImgUrl()),
                    article,null,articleSearch.getComment_num());
            articleDTOList.add(articleDTO);
        });
        PageDTO<ArticleDTO> articleDTOPageDTO = new PageDTO<>(articleDTOList,articleSearchPage.getTotalElements(),
                (long) articleSearchPage.getTotalPages());
        return articleDTOPageDTO;
    }

    @Override
    public ResultVo searchComment(int page, String order, String sort, String searchContent) {
        Pageable pageable = PageRequest.of(page,PAGE_SIZE,sort(order, sort));
        Page<CommentSearch> commentSearches = commentSearchRepository.
                findAllByContent(searchContent,pageable);
        List<CommentDTO> commentList = new ArrayList<>();
        commentSearches.getContent().forEach(commentSearch -> {
            User user = userService.findById(commentSearch.getFromuid());
            CommentDTO commentDTO = new CommentDTO(commentSearch, user);
            commentList.add(commentDTO);
        });
        PageDTO<CommentDTO> comments = new PageDTO<>(commentList,commentSearches.getTotalElements(),
                (long) commentSearches.getTotalPages());
        return ResultVoUtil.success(comments);
    }

    @Override
    public ResultVo searchReply(int page, String order, String sort, String searchContent) {
        Pageable pageable = PageRequest.of(page,PAGE_SIZE,sort(order, sort));
        Page<ReplySearch> videoReplySearches = replySearchRepository.findAllByContent(searchContent,pageable);
        List<ReplyDTO> replyDTOList = new ArrayList<>();
        videoReplySearches.getContent().forEach(replySearch -> {
            Reply reply = new Reply();
            reply.setContent(replySearch.getContent());
            reply.setCreateDate(replySearch.getCreatedate());
            reply.setId(replySearch.getId());
            User user = userService.findById(replySearch.getFromuid());
            ReplyDTO replyDTO = new ReplyDTO(reply,
                    videoReplyLikeService.countAllById(reply.getId()),
                    new SimpleUserDTO(user.getId(),user.getUserInfo().getNickname(),
                            user.getUserInfo().getHeadImgUrl()));
            replyDTOList.add(replyDTO);
        });
        PageDTO<ReplyDTO> replies = new PageDTO<>(replyDTOList,videoReplySearches.getTotalElements(),
                (long)videoReplySearches.getTotalPages());
        return ResultVoUtil.success(replies);
    }

    @Override
    public void deleteVideoById(Long id) {
        videoSearchRepository.deleteById(id);
    }

    @Override
    public void deleteAllByTopicId(Long id, Topic topic) {
        commentSearchRepository.deleteAllByTopidAndTopic(id,topic);
    }

    @Override
    public void deleteReplyAllByComment_idIn(List<Long> ids) {
        replySearchRepository.deleteAllByCommentidIn(ids);
    }

    @Override
    public void deleteCommentById(Long id) {
        commentSearchRepository.deleteById(id);
    }

    @Override
    public void deleteReplyAllByCommentId(Long id) {
        replySearchRepository.deleteAllByCommentid(id);
    }

    @Override
    public void deleteReplyById(Long id) {
        replySearchRepository.deleteById(id);
    }


    private SearchQuery getVideoSearchQuery(int page,int size,String order,String sort,String searchContent){
        return getSearchQuery(page, size, order, sort, searchContent, "introduction");
    }

    private SearchQuery getArticleSearchQuery(int page,int size,String order,String sort,String searchContent){
        return getSearchQuery(page, size, order, sort, searchContent, "text");
    }

    private SearchQuery getSearchQuery(int page, int size, String order, String sort, String searchContent, String filed) {
        FunctionScoreQueryBuilder.FilterFunctionBuilder [] functionBuilders  = new FunctionScoreQueryBuilder.FilterFunctionBuilder[2];
        functionBuilders[0] = new FunctionScoreQueryBuilder
                .FilterFunctionBuilder( QueryBuilders.matchPhraseQuery("title",searchContent),
                ScoreFunctionBuilders.weightFactorFunction(1000));
        functionBuilders[1]=new FunctionScoreQueryBuilder
                .FilterFunctionBuilder( QueryBuilders.matchPhraseQuery(filed,searchContent),
                ScoreFunctionBuilders.weightFactorFunction(500));

        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(
                functionBuilders
        ).scoreMode(FiltersFunctionScoreQuery.ScoreMode.fromString(SCORE_MODE_SUM)).setMinScore(MIN_SCORE);
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }

    private Sort sort(String order,String sort){
        Sort st;
        if ("asc".equals(order)){
            st = new Sort(Sort.Direction.ASC,sort);
        }else {
            st = new Sort(Sort.Direction.DESC,sort);
        }
        return st;
    }

}
