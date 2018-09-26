package com.example.multimedia.service.impl.mainserviceimpl;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.maindomian.Article;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.Video;
import com.example.multimedia.domian.maindomian.TopicLike;
import com.example.multimedia.repository.TopicLikeRepository;
import com.example.multimedia.service.*;
import com.example.multimedia.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/07 15:02
 */
@Service(value = "LikeService")
public class LikeServiceImpl implements LikeService {

    @Autowired
    private UserService userService;

    @Autowired
    private TopicLikeRepository topicLikeRepository;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NoticeService noticeService;

    @Override
    public Long like(Long topId, Topic topic) {
        if (topic.equals(Topic.VIDEO)){
            Video video = videoService.findById(topId);
            if (video==null){
                return null;
            }
            Long userId = getUid();
            TopicLike topicLike = status(topId,userId,topic);
            topicLike = getTopicLike(topId, topic, userId, topicLike);
            if (topicLike.isStatus()){
                video.setLikeCount(video.getLikeCount()+1);
            }else {
                video.setLikeCount(video.getLikeCount()-1);
            }
            videoService.save(video);
            topicLikeRepository.save(topicLike);
            return  video.getLikeCount();
        }else {
            Article article = articleService.findById((long) topId);
            if (article==null){
                return null;
            }
            Long userId = getUid();
            TopicLike topicLike = status(topId,userId,topic);
            topicLike = getTopicLike(topId, topic, userId, topicLike);
            if (topicLike.isStatus()){
                article.setLikeCount(article.getLikeCount()+1);
            }else {
                article.setLikeCount(article.getLikeCount()-1);
            }
            articleService.saveArticle(article);
            topicLikeRepository.save(topicLike);
            return  article.getLikeCount();
        }
    }

    private TopicLike getTopicLike(Long topId, Topic topic, Long userId, TopicLike topicLike) {
        if (topicLike == null) {
            topicLike = new TopicLike();
            topicLike.setStatus(true);
            topicLike.setTopId(topId);
            topicLike.setUserId(getUid());
            topicLike.setTopic(topic);
            String title;
            if (topic.equals(Topic.VIDEO)){
                Video video = videoService.findById(topicLike.getTopId());
                title = video.getTitle();
                noticeService.saveNotice(topic, topId, title,null,null, null,
                        userId, video.getUserId(), topic.toString().toLowerCase() + "Praise");
            }else {
                Article article = articleService.findById((long)topicLike.getTopId());
                title = article.getTitle();
                noticeService.saveNotice(topic, topId, title,null,null, null,
                        userId, article.getUserId(), topic.toString().toLowerCase() + "Praise");
            }
        } else {
            topicLike.setStatus(!topicLike.isStatus());
        }
        return topicLike;
    }

    @Override
    public Long countAllById(Long videoId) {
        return topicLikeRepository.countByTopIdAndStatus(videoId,true);
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
        return;
    }

    @Override
    public void deleteAllById(Long id) {
        topicLikeRepository.deleteAllByTopId(id);
    }

    @Override
    public void deleteAllById(Long id, Topic topic) {
        topicLikeRepository.deleteAllByTopIdAndTopic(id,topic);
    }

    @Override
    public TopicLike status(Long topId, Long userId,Topic topic){
        return topicLikeRepository.findByUserIdAndTopIdAndTopic(getUid(),topId, topic);
    }

    private Long getUid(){
        User user = userService.findByUsername(UserUtil.getUserName());
        if (user!=null){
            return user.getId();
        }
        return null;
    }

}
