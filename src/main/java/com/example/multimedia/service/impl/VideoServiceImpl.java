package com.example.multimedia.service.impl;

import com.example.multimedia.dto.VideosDTO;
import com.example.multimedia.domian.Tags;
import com.example.multimedia.domian.Video;
import com.example.multimedia.repository.TagsRepository;
import com.example.multimedia.repository.VideoRepository;
import com.example.multimedia.service.FileService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.service.VideoService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public ResultVo uploadVideo(String title,String introduction,String tag,MultipartFile multipartFile) {
        Video video = new Video();
        if (multipartFile==null){
            video.setTitle(title);
            video.setIntroduction(introduction);
            video.setUserId(getUserId(UserUtil.getUserName()));
            return saveVideo(video, tagsRepository.findByTag(tag));
        }
        if (!multipartFile.getOriginalFilename().contains(PREFIX_VIDEO)){
            ResultVo resultVo = fileService.uploadFile(multipartFile);
            if (resultVo.getCode()==0){
                return resultVo;
            }
            video.setTitle(title);
            video.setIntroduction(introduction);
            video.setUserId(getUserId(UserUtil.getUserName()));
            video.setVideoUrl(resultVo.getData().toString());
            return saveVideo(video, tagsRepository.findByTag(tag));
        }else {
            return ResultVoUtil.error(0,"请注意上传的文件为视频");
        }

    }

    @Override
    public ResultVo findMyVideos(int page,String order) {
        int size = 10;
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Video> videos = videoRepository.findAll(pageable);
        VideosDTO videosDTO = new VideosDTO(videos.getContent(),videos.getTotalElements(),
                (long) videos.getTotalPages());
        return ResultVoUtil.success(videosDTO);
    }

    @Override
    public ResultVo deleteById(long id) {
        videoRepository.deleteByIdAndUserId(id,getUserId(UserUtil.getUserName()));
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo updateVideo(long id,String title, String introduction, String tag) {
        Video video = videoRepository.findByIdAndUserId(id,getUserId(UserUtil.getUserName()));
        video.setTitle(title);
        video.setIntroduction(introduction);
        Tags tags = tagsRepository.findByTag(tag);
        return saveVideo(video,tags);
    }

    @Override
    public ResultVo findById(long id) {
        return ResultVoUtil.success(videoRepository.findById(id));
    }

    private ResultVo saveVideo(Video video, Tags tags) {
        if (tags!=null){
            video.setTags(tags);
            videoRepository.save(video);
            return ResultVoUtil.success();
        }else {
            return ResultVoUtil.error(0,"分类不存在,请检查你选择的分类");
        }
    }

    public long getUserId(String username){
        return userService.findByUsername(username).getId();
    }


}
