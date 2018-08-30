package com.example.multimedia.service.impl.mainserviceimpl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.VideoHistory;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.TopicLike;
import com.example.multimedia.domian.maindomian.tag.SmallTags;
import com.example.multimedia.dto.*;
import com.example.multimedia.domian.maindomian.Tags;
import com.example.multimedia.domian.maindomian.Video;
import com.example.multimedia.repository.VideoHistoryRepository;
import com.example.multimedia.repository.VideoRepository;
import com.example.multimedia.service.*;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
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
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/03 16:39
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class VideoServiceImpl implements VideoService {

    private final static String PREFIX_VIDEO="video/";

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private SearchService searchService;

    @Autowired
    private VideoHistoryRepository videoHistoryRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AdminNoticeService adminNoticeService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private SmallTagsService smallTagsService;

    @Autowired
    @Qualifier(value = "LikeService")
    private LikeService likeService;

    @Autowired
    private TagsService tagsService;

    @Override
    public ResultVo uploadVideo(String title, String introduction, String tag, Set<String> smallTags,
                                MultipartFile multipartFile) {
        Video video = new Video();
        if (multipartFile==null){
            video.setTitle(title);
            video.setIntroduction(introduction);
            video.setUserId(getUid());
            return saveVideo(video, tagsService.findByTag(tag),smallTags);
        }
        if (!multipartFile.getOriginalFilename().contains(PREFIX_VIDEO)){
            ResultVo resultVo = fileService.uploadFile(multipartFile);
            if (resultVo.getCode()==0){
                return resultVo;
            }
            video.setTitle(title);
            video.setIntroduction(introduction);
            video.setUserId(getUid());
            video.setVideoUrl(resultVo.getData().toString());
            return saveVideo(video, tagsService.findByTag(tag),smallTags);
        }else {
            return ResultVoUtil.error(0,"请注意上传的文件为视频");
        }

    }

    @Override
    public ResultVo findMyVideos(int page,String order,boolean isEnable) {
        int size = 10;
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Video> videos = videoRepository.findAllByUserIdAndEnable(pageable,getUid(),isEnable);
        VideosDTO videosDTO = getVideosDTO(videos);
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public ResultVo findVideos(int page, int size,String order,String sort,Boolean enable) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<Video> videos = videoRepository.findAllByEnable(pageable,enable);
        VideosDTO videosDTO = getVideosDTO(videos);
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public ResultVo findAllByTag(int page, int size, String order, String tag) {
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Video> videos = videoRepository.findAllByEnableAndTagsTag(pageable,true,tag);
        VideosDTO videosDTO = getVideosDTO(videos);
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public ResultVo findAllByUserId(int page, int size, String order, Long userId) {
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Video> videos = videoRepository.findAllByUserIdAndEnable(pageable,userId,true);
        VideosDTO videosDTO = getVideosDTO(videos);
        return ResultVoUtil.success(videosDTO);
    }

    private VideosDTO getVideosDTO(Page<Video> videos) {
        List<VideoDTO> videoDTOS = new ArrayList<>();
        for (Video video : videos.getContent()) {
            Set<SmallTagDTO> smallTagDTOS = new HashSet<>();
            video.getSmallTags().forEach(smallTags -> {
                SmallTagDTO smallTagDTO = new SmallTagDTO(smallTags);
                smallTagDTOS.add(smallTagDTO);
            });
            User user = userService.findById(video.getUserId());
            VideoDTO videoDTO = new VideoDTO(new SimpleUserDTO(user.getId(),user.getUserInfo().getNickname(),
                    user.getUserInfo().getHeadImgUrl()),
                    video, smallTagDTOS);
            videoDTOS.add(videoDTO);
        }
        return new VideosDTO(videoDTOS, videos.getTotalElements(),
                (long) videos.getTotalPages());
    }

    @Override
    public ResultVo deleteById(long id) {
        videoRepository.deleteByIdAndUserId(id,getUid());
        searchService.deleteVideoById(id);
        commentService.deleteAllBycontentId(id, Topic.VIDEO);
        likeService.deleteAllById(id,Topic.VIDEO);
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo updateVideo(long id,String title, String introduction, String tag, Set<String> smallTags) {
        Video video = videoRepository.findByIdAndUserId(id,getUid());
        if (introduction.length()<10){
            return ResultVoUtil.error(0,"请输入介绍信息不少于10个字");
        }
        video.setTitle(title);
        video.setIntroduction(introduction);
        Tags tags = tagsService.findByTag(tag);
        return saveVideo(video,tags,smallTags);
    }

    @Override
    public ResultVo findById(long id) {
        boolean isLike = false;
        Long userId = getUid();
        TopicLike topicLike = (TopicLike) likeService.status(id,userId,Topic.VIDEO);
        if (topicLike !=null){
            isLike = topicLike.isStatus();
        }
        Set<SmallTagDTO> smallTagDTOS = new HashSet<>();
        Video video = videoRepository.findById(id);
        video.getSmallTags().forEach(smallTags -> {
            SmallTagDTO smallTagDTO = new SmallTagDTO(smallTags);
            smallTagDTOS.add(smallTagDTO);
        });
        VideoDTO videoDTO = new VideoDTO(
                new SimpleUserDTO(getUser(UserUtil.getUserName())),
                video,
                isLike,smallTagDTOS);
        return ResultVoUtil.success(videoDTO);
    }

    @Override
    public Video findById(Long id) {
        Optional<Video> video = videoRepository.findById(id);
        return video.orElse(null);
    }

    @Override
    public Video save(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public void play(Long videoId) {
        Video video = findById(videoId);
        video.setPlayCount(video.getPlayCount()+1);
        save(video);
        saveHistory(videoId);
    }

    @Override
    public void saveHistory(Long videoId) {
        Long userId = getUid();
        VideoHistory videoHistory = videoHistoryRepository.findByUserIdAndVideoId(userId,videoId);
        if (videoHistory==null){
            videoHistory = new VideoHistory();
            videoHistory.setUserId(getUid());
            videoHistory.setVideoId(videoId);
        }else {
            videoHistory.setWatchTime(new Timestamp(System.currentTimeMillis()));
        }
        videoHistoryRepository.save(videoHistory);
    }


    @Override
    public ResultVo enableVideo(Long videoId,Boolean enable) {
        Video video = findById(videoId);
        video.setEnable(enable);
        save(video);
        if (enable){
            noticeService.saveNotice(Topic.VIDEO,videoId,video.getTitle(),null,null,null,
                    null,video.getUserId(),"enable");
        }else {
            deleteById(videoId);
            noticeService.saveNotice(Topic.VIDEO,videoId,video.getTitle(),null,null,null,
                    null,video.getUserId(),"unEnable");
        }
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo getHistory(int page) {
        int size = 10;
        Sort sort = new Sort(Sort.Direction.DESC,"watchTime");
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<VideoHistory> videoHistories = videoHistoryRepository.findAllByUserId(getUid(),pageable);
        List<VideoHistoryDTO> videoHistoryDTOS = new ArrayList<>();
        videoHistories.forEach(videoHistory -> {
            VideoHistoryDTO videoHistoryDTO = new VideoHistoryDTO();
            videoHistoryDTO.setVideoId(videoHistory.getVideoId());
            videoHistoryDTO.setTitle(findById(videoHistory.getVideoId()).getTitle());
            videoHistoryDTO.setWatchTime(videoHistory.getWatchTime());
            videoHistoryDTOS.add(videoHistoryDTO);
        });
        PageDTO<VideoHistoryDTO> videoHistoryPageDTO = new PageDTO<>(videoHistoryDTOS,videoHistories.getTotalElements(),
                (long)videoHistories.getTotalPages());
        return ResultVoUtil.success(videoHistoryPageDTO);
    }

    @Override
    public void deleteHistory() {
        videoHistoryRepository.deleteAllByWatchTimeBefore(new Timestamp(System.currentTimeMillis()-259200000));
    }

    @Override
    public ResultVo reportVideo(Long videoId, String reason, String content) {
        Video video = findById(videoId);
        if (video== null){
            return ResultVoUtil.error(0,"发生错误");
        }
        adminNoticeService.save(videoId,Topic.VIDEO,video.getTitle(),
                reason,content,"report");
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo findAllBySmallTag(int page, int size, String smallTag, String sort) {
        Sort s = new Sort(Sort.Direction.DESC,sort);
        Pageable pageable = PageRequest.of(page,size,s);
        Page<Video> videoPage = videoRepository.findAllBySmallTags(smallTagsService.findBySmallTag(smallTag),pageable);
        VideosDTO videosDTO = getVideosDTO(videoPage);
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public int countVideosForDays(int day) {
        return videoRepository.countVideosForDays(day);
    }

    private ResultVo saveVideo(Video video, Tags tags,Set<String> smallTags) {
        Set<SmallTags> smallTagsSet = smallTagsService.findAllBySmallTag(smallTags);
        if (tags!=null){
           if (smallTagsSet.size()>0){
               video.setTags(tags);
               video.setSmallTags(smallTagsSet);
               save(video);
               adminNoticeService.save(video.getId(),Topic.VIDEO,video.getTitle(),"confirm");
               return ResultVoUtil.success();
           }
            return ResultVoUtil.error(0,"必须选择一个分类");
        }else {
            return ResultVoUtil.error(0,"分类不存在,请检查你选择的分类");
        }
    }

    private User getUser(String username){
        return userService.findByUsername(username);
    }

    private Long getUid(){
        User user = userService.findByUsername(UserUtil.getUserName());
        if (user!=null){
            return user.getId();
        }
        return null;
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
