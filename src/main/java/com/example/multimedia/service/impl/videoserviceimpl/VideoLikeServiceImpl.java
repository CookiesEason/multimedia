package com.example.multimedia.service.impl.videoserviceimpl;

import com.example.multimedia.domian.videodomian.Video;
import com.example.multimedia.domian.videodomian.VideoLike;
import com.example.multimedia.repository.VideoLikeRepository;
import com.example.multimedia.service.LikeService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.service.VideoService;
import com.example.multimedia.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/07 15:02
 */
@Service(value = "VideoLikeService")
public class VideoLikeServiceImpl implements LikeService {

    @Autowired
    private UserService userService;

    @Autowired
    private VideoLikeRepository videoLikeRepository;

    @Autowired
    private VideoService videoService;

    @Override
    public void like(Long videoId) {
        if (videoService.findById(videoId)==null){
            return;
        }
        Video video = videoService.findById(videoId);
        VideoLike videoLike = status(videoId);
        if (videoLike == null){
            videoLike = new VideoLike();
            videoLike.setStatus(true);
            videoLike.setVideoId(videoId);
            videoLike.setUserId(getUid());
            video.setLikeCount(video.getLikeCount()+1);
        }else {
            videoLike.setStatus(!videoLike.isStatus());
            if (videoLike.isStatus()){
                video.setLikeCount(video.getLikeCount()+1);
            }else {
                video.setLikeCount(video.getLikeCount()-1);
            }
        }
        videoService.save(video);
        videoLikeRepository.save(videoLike);
    }

    @Override
    public Long countAllById(Long videoId) {
        return videoLikeRepository.countByVideoIdAndStatus(videoId,true);
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
        return;
    }

    @Override
    public void deleteAllById(Long id) {
        videoLikeRepository.deleteAllByVideoId(id);
    }

    @Override
    public VideoLike status(Long videoId){
        return videoLikeRepository.findByUserIdAndVideoId(getUid(),videoId);
    }

    private Long getUid(){
        return userService.findByUsername(UserUtil.getUserName()).getId();
    }

}
