package com.example.multimedia.service;

import com.example.multimedia.vo.ResultVo;

/**
 * @author CookiesEason
 * 2018/08/28 10:43
 */
public interface AnnouncementService {

    /**
     * 查看某篇
     * @param id
     * @return
     */
    ResultVo findOne(Long id);

    /**
     * 查询所有
     * @param page
     * @param size
     * @return
     */
    ResultVo findAll(int page,int size);

    /**
     * 分类查询
     * @param tag
     * @param page
     * @param size
     * @return
     */
    ResultVo findByTag(String tag,int page,int size);

    /**
     * 发布公告
     * @param title
     * @param text
     * @param tag
     * @return
     */
    ResultVo save(String title,String text,String tag);

    /**
     * 更新公告
     * @param id
     * @param title
     * @param text
     * @param tag
     * @return
     */
    ResultVo update(Long id,String title,String text,String tag);

    /**
     * 删除公告
     * @param id
     * @return
     */
    ResultVo delete(Long id);

}
