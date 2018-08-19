package com.example.multimedia.service.impl.mainserviceimpl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.maindomian.Video;
import com.example.multimedia.domian.maindomian.Comment;
import com.example.multimedia.domian.maindomian.Reply;
import com.example.multimedia.domian.maindomian.search.VideoCommentSearch;
import com.example.multimedia.domian.maindomian.search.VideoReplySearch;
import com.example.multimedia.domian.maindomian.search.VideoSearch;
import com.example.multimedia.dto.*;
import com.example.multimedia.repository.TagsRepository;
import com.example.multimedia.repository.search.VideoCommentSearchRepository;
import com.example.multimedia.repository.search.VideoReplySearchRepository;
import com.example.multimedia.repository.search.VideoSearchRepository;
import com.example.multimedia.service.LikeService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.service.VideoSearchService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.elasticsearch.common.lucene.search.function.FiltersFunctionScoreQuery;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
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
import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/12 15:44
 */
@Service
public class VideoSearchServiceImpl implements VideoSearchService {

    String SCORE_MODE_SUM = "sum";
    int PAGE_SIZE = 10;
    Float  MIN_SCORE = 10.0F;

    @Autowired
    private VideoSearchRepository videoSearchRepository;

    @Autowired
    private VideoCommentSearchRepository commentSearchRepository;

    @Autowired
    private VideoReplySearchRepository replySearchRepository;

    @Autowired
    @Qualifier(value = "CommentLikeService")
    private LikeService videoCommentLikeService;

    @Autowired
    @Qualifier(value = "ReplyLikeService")
    private LikeService videoReplyLikeService;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private UserService userService;

    @Override
    public ResultVo searchVideo(int page,String order,String sort,String searchContent,Boolean enable) {
        SearchQuery searchQuery = getVideoSearchQuery(page,PAGE_SIZE,order,sort,searchContent);
        Page<VideoSearch> videoSearchPage = videoSearchRepository.search(searchQuery);
        List<VideoDTO> videoDTOS = new ArrayList<>();
        videoSearchPage.getContent().forEach(videoSearch -> {
            if (enable.equals(videoSearch.getEnable())){
                Video video = new Video(videoSearch,tagsRepository.findById(videoSearch.getTags_id()).get());
                VideoDTO videoDTO = new VideoDTO(new SimpleUserDTO(userService.findById(video.getUserId())),video);
                videoDTOS.add(videoDTO);
            }
        });
        VideosDTO videosDTO = new VideosDTO(videoDTOS,videoSearchPage.getTotalElements(),
                (long) videoSearchPage.getTotalPages());
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public ResultVo searchVideoComment(int page, String order, String sort, String searchContent) {
        Pageable pageable = PageRequest.of(page,PAGE_SIZE,sort(order, sort));
        Page<VideoCommentSearch> commentSearches = commentSearchRepository.
                findAllByContent(searchContent,pageable);
        List<CommentDTO> commentList = new ArrayList<>();
        commentSearches.getContent().forEach(videoCommentSearch -> {
            User user = userService.findById(videoCommentSearch.getFromuid());
            Comment comment = new Comment();
            comment.setId(videoCommentSearch.getId());
            comment.setTopId(videoCommentSearch.getVideoid());
            comment.setTopId(videoCommentSearch.getId());
            comment.setContent(videoCommentSearch.getContent());
            comment.setFromUid(videoCommentSearch.getFromuid());
            comment.setCreateDate(videoCommentSearch.getCreatedate());
            CommentDTO commentDTO = new CommentDTO(comment,
                    videoCommentLikeService.countAllById(comment.getId())
                    ,user);
            commentList.add(commentDTO);
        });
        PageDTO<CommentDTO> comments = new PageDTO<>(commentList,commentSearches.getTotalElements(),
                (long) commentSearches.getTotalPages());
        return ResultVoUtil.success(comments);
    }

    @Override
    public ResultVo searchVideoReply(int page, String order, String sort, String searchContent) {
        Pageable pageable = PageRequest.of(page,PAGE_SIZE,sort(order, sort));
        Page<VideoReplySearch> videoReplySearches = replySearchRepository.findAllByContent(searchContent,pageable);
        List<ReplyDTO> replyDTOList = new ArrayList<>();
        videoReplySearches.getContent().forEach(videoReplySearch -> {
            Reply reply = new Reply();
            reply.setContent(videoReplySearch.getContent());
            reply.setCreateDate(videoReplySearch.getCreatedate());
            reply.setId(videoReplySearch.getId());
            ReplyDTO replyDTO = new ReplyDTO(reply,
                    videoReplyLikeService.countAllById(reply.getId()),
                    new SimpleUserDTO(userService.findById(videoReplySearch.getFromuid())));
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
    public void deleteAllByVideoId(Long id) {
        commentSearchRepository.deleteAllByVideoid(id);
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
        FunctionScoreQueryBuilder.FilterFunctionBuilder [] functionBuilders  = new FunctionScoreQueryBuilder.FilterFunctionBuilder[2];
        functionBuilders[0] = new FunctionScoreQueryBuilder
                .FilterFunctionBuilder( QueryBuilders.matchPhraseQuery("title",searchContent),
                ScoreFunctionBuilders.weightFactorFunction(1000));
        functionBuilders[1]=new FunctionScoreQueryBuilder
                .FilterFunctionBuilder( QueryBuilders.matchPhraseQuery("introduction",searchContent),
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
