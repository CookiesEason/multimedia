package com.example.multimedia.service;

import com.example.multimedia.domian.maindomian.Tags;
import com.example.multimedia.vo.ResultVo;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/18 17:20
 */
public interface TagsService {

    /**
     * 获取标签
     * @return
     */
    List<Tags> getTags();

    /**
     * 更新标签
     * @param tag
     * @return
     */
    ResultVo updateTag(String oldTag,String tag);

    /**
     * 添加视频标签
     * @param tag
     * @return
     */
    ResultVo addTag(String tag);

    /**
     * 删除视频标签
     */
    ResultVo deleteTag(String tag);

    Tags findByTag(String tag);

    ResultVo hotWorksByTag(String tag);

}
