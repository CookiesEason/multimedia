package com.example.multimedia.service.impl.videoserviceimpl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.videodomian.VideoComment;
import com.example.multimedia.domian.videodomian.VideoLike;
import com.example.multimedia.dto.SimpleUserDTO;
import com.example.multimedia.dto.VideoDTO;
import com.example.multimedia.dto.VideosDTO;
import com.example.multimedia.domian.videodomian.Tags;
import com.example.multimedia.domian.videodomian.Video;
import com.example.multimedia.repository.TagsRepository;
import com.example.multimedia.repository.VideoRepository;
import com.example.multimedia.repository.search.VideoSearchRepository;
import com.example.multimedia.service.*;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private TagsRepository tagsRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoSearchService videoSearchService;

    @Autowired
    @Qualifier(value = "VideoCommentService")
    private CommentService videoCommentService;

    @Autowired
    @Qualifier(value = "VideoLikeService")
    private LikeService videoLikeService;

    @Override
    @Cacheable(value = "tags")
    public ResultVo getTags() {
        return ResultVoUtil.success(tagsRepository.findAll());
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
    public ResultVo uploadVideo(String title,String introduction,String tag,MultipartFile multipartFile) {
        Video video = new Video();
        if (multipartFile==null){
            video.setTitle(title);
            video.setIntroduction(introduction);
            video.setUserId(getUid());
            return saveVideo(video, tagsRepository.findByTag(tag));
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
            return saveVideo(video, tagsRepository.findByTag(tag));
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
        List<VideoDTO> videoDTOS = new ArrayList<>();
        for (Video video :videos.getContent()){
            VideoDTO videoDTO = new VideoDTO(new SimpleUserDTO(userService.findById(video.getUserId())),video);
            videoDTOS.add(videoDTO);
        }
        VideosDTO videosDTO = new VideosDTO(videoDTOS,videos.getTotalElements(),
                (long) videos.getTotalPages());
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public ResultVo findVideos(int page, int size,String order,String sort,Boolean enable) {
        Pageable pageable = PageRequest.of(page,size,sort(order, sort));
        Page<Video> videos = videoRepository.findAllByEnable(pageable,enable);
        List<VideoDTO> videoDTOS = new ArrayList<>();
        for (Video video :videos.getContent()){
            VideoDTO videoDTO = new VideoDTO(new SimpleUserDTO(userService.findById(video.getUserId())),video);
            videoDTOS.add(videoDTO);
        }
        VideosDTO videosDTO = new VideosDTO(videoDTOS,videos.getTotalElements(),
                (long) videos.getTotalPages());
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public ResultVo findAllByTag(int page, int size, String order, String tag) {
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Video> videos = videoRepository.findAllByEnableAndTagsTag(pageable,true,tag);
        List<VideoDTO> videoDTOS = new ArrayList<>();
        for (Video video :videos.getContent()){
            VideoDTO videoDTO = new VideoDTO(new SimpleUserDTO(userService.findById(video.getUserId())),video);
            videoDTOS.add(videoDTO);
        }
        VideosDTO videosDTO = new VideosDTO(videoDTOS,videos.getTotalElements(),
                (long) videos.getTotalPages());
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public ResultVo findAllByUserId(int page, int size, String order, Long userId) {
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Video> videos = videoRepository.findAllByUserIdAndEnable(pageable,userId,true);
        List<VideoDTO> videoDTOS = new ArrayList<>();
        for (Video video :videos.getContent()){
            VideoDTO videoDTO = new VideoDTO(new SimpleUserDTO(userService.findById(video.getUserId())),video);
            videoDTOS.add(videoDTO);
        }
        VideosDTO videosDTO = new VideosDTO(videoDTOS,videos.getTotalElements(),
                (long) videos.getTotalPages());
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public ResultVo deleteById(long id) {
        videoRepository.deleteByIdAndUserId(id,getUid());
        videoSearchService.deleteVideoById(id);
        videoCommentService.deleteAllBycontentId(id);
        videoLikeService.deleteAllById(id);
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo updateVideo(long id,String title, String introduction, String tag) {
        Video video = videoRepository.findByIdAndUserId(id,getUid());
        if (introduction.length()<10){
            return ResultVoUtil.error(0,"请输入介绍信息不少于10个字");
        }
        video.setTitle(title);
        video.setIntroduction(introduction);
        Tags tags = tagsRepository.findByTag(tag);
        return saveVideo(video,tags);
    }

    @Override
    public ResultVo findById(long id) {
        boolean isLike = false;
        VideoLike videoLike = (VideoLike) videoLikeService.status(id);
        if (videoLike!=null){
            isLike = videoLike.isStatus();
        }
        VideoDTO videoDTO = new VideoDTO(
                new SimpleUserDTO(getUser(UserUtil.getUserName())),
                videoRepository.findById(id),
                isLike);
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
    }

    @Override
    public ResultVo enableVideo(Long videoId) {
        Video video = findById(videoId);
        video.setEnable(true);
        save(video);
        return ResultVoUtil.success();
    }

    private ResultVo saveVideo(Video video, Tags tags) {
        if (tags!=null){
            video.setTags(tags);
            save(video);
            return ResultVoUtil.success();
        }else {
            return ResultVoUtil.error(0,"分类不存在,请检查你选择的分类");
        }
    }

    private User getUser(String username){
        return userService.findByUsername(username);
    }

    private Long getUid(){
        return userService.findByUsername(UserUtil.getUserName()).getId();
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
