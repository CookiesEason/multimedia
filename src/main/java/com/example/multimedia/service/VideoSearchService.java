package com.example.multimedia.service;

import com.example.multimedia.vo.ResultVo;

/**
 * @author CookiesEason
 * 2018/08/12 15:41
 */
public interface VideoSearchService {

    /**
     *搜索视频
     * @param page
     * @param order
     * @param sort
     * @param searchContent
     * @return
     */
    ResultVo searchVideo(int page,String order,String sort,String searchContent);

    /**
     *搜索评论
     * @param page
     * @param order
     * @param sort
     * @param searchContent
     * @return
     */
    ResultVo searchVideoComment(int page,String order,String sort,String searchContent);

    /**
     * 搜索回复
     * @param page
     * @param order
     * @param sort
     * @param searchContent
     * @return
     */
    ResultVo searchVideoReply(int page,String order,String sort,String searchContent);

}
