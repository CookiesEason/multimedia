package com.example.multimedia.service.impl;

import com.example.multimedia.domian.Picture;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.repository.PictureRepository;
import com.example.multimedia.service.PictureService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author CookiesEason
 * 2018/09/09 14:06
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public ResultVo findAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Picture> picturePage = pictureRepository.findAll(pageable);
        PageDTO<Picture> pageDTO = new PageDTO<>(picturePage.getContent(),picturePage.getTotalElements(),(long)picturePage.getTotalPages());
        return ResultVoUtil.success(pageDTO);
    }

    @Override
    public ResultVo findByType(String type, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Picture> picturePage = pictureRepository.findAllByType(type,pageable);
        PageDTO<Picture> pageDTO = new PageDTO<>(picturePage.getContent(),picturePage.getTotalElements(),(long)picturePage.getTotalPages());
        return ResultVoUtil.success(pageDTO);
    }

    @Override
    public ResultVo save(String imgUrl, String type) {
        Picture picture = new Picture();
        picture.setImgUrl(imgUrl);
        picture.setType(type);
        return ResultVoUtil.success(pictureRepository.save(picture));
    }

    @Override
    public ResultVo update(Long id, String imgUrl, String type) {
        Optional<Picture> pictureOptional = pictureRepository.findById(id);
        if (pictureOptional.isPresent()){
            Picture picture = pictureOptional.get();
            picture.setImgUrl(imgUrl);
            picture.setType(type);
            return ResultVoUtil.success(picture);
        }
        return ResultVoUtil.error(0,"原图不存在");
    }

    @Override
    public ResultVo delete(Long id) {
        pictureRepository.deleteById(id);
        return ResultVoUtil.success();
    }
}
