package com.example.multimedia.service.impl.mainserviceimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.multimedia.domian.CommandHistory;
import com.example.multimedia.domian.User;
import com.example.multimedia.domian.VideoHistory;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.TopicLike;
import com.example.multimedia.domian.maindomian.tag.SmallTags;
import com.example.multimedia.dto.*;
import com.example.multimedia.domian.maindomian.Tags;
import com.example.multimedia.domian.maindomian.Video;
import com.example.multimedia.properties.TengXunProperties;
import com.example.multimedia.repository.*;
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
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
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

    private static final String HMAC_ALGORITHM = "HmacSHA1";

    private static final String CONTENT_CHARSET = "UTF-8";

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

    @Autowired
    private FollowerService followerService;

    @Autowired
    private TopicLikeRepository topicLikeRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TengXunProperties tengXunProperties;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommandHistoryService commandHistoryService;

    @Override
    public String getUploadSignature() {
        String secretId = tengXunProperties.getAccessKey();
        String secretKey = tengXunProperties.getSecretKey();
        long currentTime = System.currentTimeMillis() / 1000;
        int random = new Random().nextInt(java.lang.Integer.MAX_VALUE);
        int signValidDuration = 3600 * 24 * 2;
        String strSign = "";
        String contextStr = "";

        long endTime = (currentTime + signValidDuration);
        try {
            contextStr += "secretId=" + java.net.URLEncoder.encode(secretId, "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        contextStr += "&currentTimeStamp=" + currentTime;
        contextStr += "&expireTime=" + endTime;
        contextStr += "&random=" + random;
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(CONTENT_CHARSET), mac.getAlgorithm());
            mac.init(key);

            byte[] hash = mac.doFinal(contextStr.getBytes(CONTENT_CHARSET));
            byte[] sigBuf = byteMerger(hash, contextStr.getBytes("utf8"));
            strSign = new String(new BASE64Encoder().encode(sigBuf).getBytes());
            strSign = strSign.replace(" ", "").replace("\n", "").replace("\r", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strSign;
    }

    @Override
    public ResultVo uploadVideo(String title, String introduction, String tag, Set<String> smallTags,
                                String imgUrl,
                                String videoUrl,String fileId,Long time) {
        Video video = new Video();
        if (videoUrl==null){
            video.setTitle(title);
            video.setIntroduction(introduction);
            video.setUserId(getUid());
            video.setTime(time);
            return saveVideo(video, tagsService.findByTag(tag),smallTags);
        }
        video.setTitle(title);
        video.setIntroduction(introduction);
        video.setUserId(getUid());
        video.setVideoUrl(videoUrl);
        video.setImgUrl(imgUrl);
        video.setFileId(fileId);
        video.setTime(time);
        return saveVideo(video, tagsService.findByTag(tag),smallTags);

    }

    @Override
    public ResultVo findMyVideos(int page,int size,String order,boolean isEnable,boolean isAuditing) {
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Video> videos = videoRepository.findAllByUserIdAndEnableAndAuditing(pageable,getUid(),isEnable,isAuditing);
        VideosDTO videosDTO = getVideosDTO(videos);
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public VideosDTO findVideos(int page, int size,String order,String sort,Boolean enable,Boolean auditing) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<Video> videos = videoRepository.findAllByEnableAndAuditing(pageable,enable,auditing);
        VideosDTO videosDTO = getVideosDTO(videos);
        return videosDTO;
    }

    @Override
    public ResultVo findAllByTag(int page, int size, String order, String tag) {
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Video> videos = videoRepository.findAllByEnableAndTagsTagAndAuditing(pageable,true,tag,true);
        VideosDTO videosDTO = getVideosDTO(videos);
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public ResultVo findAllByUserId(int page, int size, String order, Long userId) {
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Video> videos = videoRepository.findAllByUserIdAndEnableAndAuditing(pageable,userId,true,true);
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
                    video, smallTagDTOS,commentService.num(video.getId(),Topic.VIDEO));
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
    public ResultVo deleteByAdmin(long id) {
        String title = videoRepository.videoTitle(id);
        videoRepository.deleteById(id);
        searchService.deleteVideoById(id);
        commentService.deleteAllBycontentId(id, Topic.VIDEO);
        likeService.deleteAllById(id,Topic.VIDEO);
        CommandHistory commandHistory = new CommandHistory();
        commandHistory.setContent("删除了视频:"+title);
        commandHistory.setCommand("删除");
        commandHistory.setType("视频");
        User u = userRepository.findByUsername(UserUtil.getUserName());
        commandHistory.setPeople(u.getRoleList().get(0).getRole());
        commandHistoryService.save(commandHistory);
        return null;
    }

    @Override
    public ResultVo updateVideo(long id,String title, String introduction, String tag,
                                Set<String> smallTags,String imgUrl) {
        Video video = videoRepository.findByIdAndUserId(id,getUid());
        if (introduction.length()<10){
            return ResultVoUtil.error(0,"请输入介绍信息不少于10个字");
        }
        video.setTitle(title);
        video.setIntroduction(introduction);
        if (imgUrl!=null){
            video.setImgUrl(imgUrl);
        }
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
        Video video = videoRepository.findByIdAndAuditingAndEnable(id,true,true);
        if (video == null){
            return ResultVoUtil.error(404,"视频不存在");
        }
        video.getSmallTags().forEach(smallTags -> {
            SmallTagDTO smallTagDTO = new SmallTagDTO(smallTags);
            smallTagDTOS.add(smallTagDTO);
        });
        VideoDTO videoDTO = new VideoDTO(
                new SimpleUserDTO(userService.findById(video.getUserId()),
                        followerService.checkFollow(video.getUserId())),
                video,
                isLike,smallTagDTOS);
        Sort sort = new Sort(Sort.Direction.DESC,"likeCount");
        Pageable pageable = PageRequest.of(0,3,sort);
        List<SimpleVideoDTO> otherVideos= new ArrayList<>();
        List<SimpleArticleDTO> otherArticles = new ArrayList<>();
        List<SimpleVideoDTO> recommendVideos = new ArrayList<>();
        List<SimpleVideoDTO> relatedVideos = new ArrayList<>();
        videoRepository.findAllByUserIdAndEnableAndAuditing(pageable,video.getUserId(),true,true).getContent()
                .forEach(simpleVideo -> {
                    SimpleVideoDTO simpleVideoDTO = new SimpleVideoDTO(simpleVideo);
                    otherVideos.add(simpleVideoDTO);
                });
        articleRepository.findAllByUserIdAndEnable(video.getUserId(),pageable,true).getContent()
                .forEach(simpleArticle -> {
                    SimpleArticleDTO simpleArticleDTO = new SimpleArticleDTO(simpleArticle);
                    otherArticles.add(simpleArticleDTO);
                });
        pageable = PageRequest.of(0,5,sort);
        videoRepository.findAllByEnableAndAuditing(pageable,true,true).getContent()
                .forEach(simpleVideo -> {
                    SimpleVideoDTO simpleVideoDTO = new SimpleVideoDTO(simpleVideo);
                    recommendVideos.add(simpleVideoDTO);
                });
        videoRepository.findAllByEnableAndTagsTagAndAuditing(pageable,true,video.getTags().getTag(),true).getContent()
                .forEach(simpleVideo -> {
                    SimpleVideoDTO simpleVideoDTO = new SimpleVideoDTO(simpleVideo);
                    relatedVideos.add(simpleVideoDTO);
                });
        VideoWorkDTO workDTO = new VideoWorkDTO();
        workDTO.setVideo(videoDTO);
        workDTO.setOtherVideos(otherVideos);
        workDTO.setOtherArticles(otherArticles);
        workDTO.setRecommendVideos(recommendVideos);
        workDTO.setRelatedVideos(relatedVideos);
        return ResultVoUtil.success(workDTO);
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
            Long uid = getUid();
            if (uid==null){
                return;
            }
            videoHistory.setUserId(uid);
            videoHistory.setVideoId(videoId);
            videoHistory.setTitle(findById(videoId).getTitle());
        }else {
            videoHistory.setWatchTime(new Timestamp(System.currentTimeMillis()));
        }
        videoHistoryRepository.save(videoHistory);
    }


    @Override
    public ResultVo enableVideo(Long videoId,Boolean enable,String reason) {
        Video video = findById(videoId);
        video.setEnable(enable);
        video.setAuditing(true);
        save(video);
        CommandHistory commandHistory = new CommandHistory();
        if (enable){
            problemService.delete(videoId,Topic.VIDEO);
            commandHistory.setContent("审核通过视频:"+video.getTitle());
            commandHistory.setCommand("审核");
            commandHistory.setType("视频");
        }else {
            problemService.save(videoId,reason,Topic.VIDEO);
            commandHistory.setContent("审核未通过视频:"+video.getTitle());
            commandHistory.setCommand("审核");
            commandHistory.setType("视频");
        }
        User u = userRepository.findByUsername(UserUtil.getUserName());
        commandHistory.setPeople(u.getRoleList().get(0).getRole());
        commandHistoryService.save(commandHistory);
        if (enable){
            noticeService.saveNotice(Topic.VIDEO,videoId,video.getTitle(),null,null,null,
                    null,video.getUserId(),"enable");
        }else {
            noticeService.saveNotice(Topic.VIDEO,videoId,video.getTitle(),null,null,null,
                    null,video.getUserId(),"unEnable");
        }
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo getHistory(int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC,"watchTime");
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<VideoHistory> videoHistories = videoHistoryRepository.findAllByUserId(getUid(),pageable);
        List<VideoHistoryDTO> videoHistoryDTOS = new ArrayList<>();
        videoHistories.forEach(videoHistory -> {
            VideoHistoryDTO videoHistoryDTO = new VideoHistoryDTO();
            videoHistoryDTO.setVideoId(videoHistory.getVideoId());
            videoHistoryDTO.setTitle(videoHistory.getTitle());
            videoHistoryDTO.setWatchTime(videoHistory.getWatchTime());
            User user = userService.findById(videoHistory.getUserId());
            videoHistoryDTO.setUserId(user.getId());
            videoHistoryDTO.setNickname(user.getUserInfo().getNickname());
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
        Page<Video> videoPage = videoRepository.findAllBySmallTagsAndEnableAndAuditing(smallTagsService.findBySmallTag(smallTag),
                pageable,true,true);
        VideosDTO videosDTO = getVideosDTO(videoPage);
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public int countVideosForDays(int day) {
        return videoRepository.countVideosForDays(day);
    }

    @Override
    public ResultVo findAllByLike(Long userId,int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Video> videoPage = videoRepository.findAllByIdInAndEnableAndAuditing(topicLikeRepository.ids(Topic.VIDEO.toString(),
                userId),pageable,true,true);
        VideosDTO videosDTO = getVideosDTO(videoPage);
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public ResultVo countWorksProportion(Long userId) {
        List<Tags> tags =  tagsService.getTags();
        JSONArray jsonArray=new JSONArray();
        for (Tags t: tags) {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",t.getTag());
            jsonObject.put("value",videoRepository.countAllByTagsTagAndUserIdAndEnableAndAuditing(t.getTag(),userId,
                    true,true));
            jsonArray.add(jsonObject);
        }
        return ResultVoUtil.success(jsonArray);
    }

    @Override
    public ResultVo count() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","最热");
        jsonObject.put("num",videoRepository.countAllByCreateDateAfterAndEnableAndAuditing(calendar.getTime(),
                true,true));
        jsonArray.add(jsonObject);
        jsonObject = new JSONObject();
        jsonObject.put("name","最新");
        jsonObject.put("num",videoRepository.count());
        jsonArray.add(jsonObject);
        tagsService.getTags().forEach(tags -> {
            JSONObject jo=new JSONObject();
            jo.put("name",tags.getTag());
            jo.put("num",videoRepository.countAllByTagsTagAndEnableAndAuditing(tags.getTag(),
                    true,true));
            jsonArray.add(jo);
        });
        return ResultVoUtil.success(jsonArray);
    }

    @Override
    public ResultVo problems(Long id) {
        return problemService.getById(id,Topic.VIDEO);
    }

    @Override
    public List<Object> worksCompare() {
        return videoRepository.works();
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

    private static byte[] byteMerger(byte[] byte1, byte[] byte2) {
        byte[] byte3 = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, byte3, 0, byte1.length);
        System.arraycopy(byte2, 0, byte3, byte1.length, byte2.length);
        return byte3;
    }

}
