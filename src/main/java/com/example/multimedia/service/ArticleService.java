package com.example.multimedia.service;

import com.example.multimedia.domian.maindomian.Article;
import com.example.multimedia.vo.ResultVo;

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
    ResultVo save(String title,String text,String tag);

    /**
     * 更新文章
     * @param articleId
     * @param title
     * @param text
     * @param tag
     * @return
     */
    ResultVo update(Long articleId,String title,String text,String tag);

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    ResultVo delete(Long articleId);


    /**
     * 获取文章
     * @param page
     * @param size
     * @param order
     * @param sort
     * @param enable
     * @return
     */
    ResultVo findAllByEnable(int page,int size,String order,String sort,Boolean enable);

    /**
     * 分类查找文章
     * @param page
     * @param size
     * @param order
     * @param sort
     * @param tag
     * @param enable
     * @return
     */
    ResultVo findAllByTag(int page,int size,String order,String sort,String tag,Boolean enable);

    /**
     * 获取自己文章
     * @param page
     * @param size
     * @param order
     * @param sort
     * @param enable
     * @return
     */
    ResultVo findMyAll(int page,int size,String order,String sort,Boolean enable);

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

    void save(Article article);

}
