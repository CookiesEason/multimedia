package com.example.multimedia.service;

import com.example.multimedia.domian.maindomian.Article;
import com.example.multimedia.vo.ResultVo;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 * @author CookiesEason
 * 2018/08/18 17:05
 */
public interface ArticleService {

    /**
     * 上传文章
     * @param title
     * @param text
     * @param tag
     * @return
     */
    ResultVo save(String title, String text, String tag, MultipartFile multipartFile, Set<String> smallTags);

    /**
     * 更新文章
     * @param articleId
     * @param title
     * @param text
     * @param tag
     * @return
     */
    ResultVo update(Long articleId,String title,String text,MultipartFile multipartFile,String tag,Set<String> smallTags);

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    ResultVo deleteById(Long articleId);


    /**
     * 获取文章
     * @param page
     * @param size
     * @param order
     * @param sort
     * @return
     */
    ResultVo findAll(int page,int size,String order,String sort);

    /**
     * 分类查找文章
     * @param page
     * @param size
     * @param order
     * @param sort
     * @param tag
     * @return
     */
    ResultVo findAllByTag(int page,int size,String order,String sort,String tag);

    /**
     * 获取自己文章
     * @param page
     * @param size
     * @param order
     * @param sort
     * @return
     */
    ResultVo findMyAll(int page,int size,String order,String sort);

    /**
     * 查看他人所有文章
     * @param page
     * @param size
     * @param order
     * @param sort
     * @return
     */
    ResultVo findUserAll(Long userId,int page,int size,String order,String sort);

    /**
     * 查看某文章
     * @param id
     * @return
     */
    ResultVo findById(Long id);

    /**
     * 查找某文章
     * @param id
     * @return
     */
    Article findById(long id);

    /**
     * 按某标签查找
     * @param page
     * @param smallTag
     * @return
     */
    ResultVo findAllBySmallTag(int page, int size,String smallTag,String sort);

    void save(Article article);

    /**
     * 举报
     * @param articleId
     * @param reason
     * @param content
     * @return
     */
    ResultVo reportArticle(Long articleId,String reason,String content);


    /**
     * 最新投稿 过去三天
     * @return
     */
    int countArticlesForDays(int day);

    ResultVo findAllByLike(Long userId, int page, int size);

    /**
     * 作品类别占比
     * @param userId
     * @return
     */
    ResultVo countWorksProportion(Long userId);
}
