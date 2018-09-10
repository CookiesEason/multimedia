package com.example.multimedia.service;

import com.example.multimedia.vo.ResultVo;

/**
 * @author CookiesEason
 * 2018/09/09 14:04
 */
public interface PictureService {

    /**
     * 查询所有
     * @param page
     * @param size
     * @return
     */
    ResultVo findAll(int page,int size);

    /**
     * 类型
     * @param type
     * @param page
     * @param size
     * @return
     */
    ResultVo findByType(String type,int page,int size);

    /**
     * 上传
     * @param imgUrl
     * @param type
     * @return
     */
    ResultVo save(String imgUrl,String type);

    /**
     * 更新
     * @param id
     * @param imgUrl
     * @param type
     * @return
     */
    ResultVo update(Long id,String imgUrl,String type);

    /**
     * 删除
     * @param id
     * @return
     */
    ResultVo delete(Long id);

}
