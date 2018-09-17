package com.example.multimedia.service.impl;

import com.example.multimedia.domian.Announcement;
import com.example.multimedia.dto.AnnouncementDTO;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.repository.AnnouncementRepository;
import com.example.multimedia.service.AnnouncementService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author CookiesEason
 * 2018/08/28 10:45
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Override
    public ResultVo findOne(Long id) {
        Optional<Announcement> announcementOptional = announcementRepository.findById(id);
        if (announcementOptional.isPresent()){
            Announcement announcement = announcementOptional.get();
            return ResultVoUtil.success(announcement);
        }
        return ResultVoUtil.error(0,"出错了");
    }

    @Override
    public ResultVo findAll(int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = PageRequest.of(page, size,sort);
        Page<Announcement> announcementPage = announcementRepository.findAll(pageable);
        List<AnnouncementDTO> announcementDTOList = new ArrayList<>();
        announcementPage.getContent().forEach(announcement -> {
            AnnouncementDTO announcementDTO = new AnnouncementDTO(announcement);
            announcementDTOList.add(announcementDTO);
        });
        PageDTO<AnnouncementDTO> announcementPageDTO = new PageDTO<>(announcementDTOList,announcementPage.getTotalElements(),
                (long)announcementPage.getTotalPages());
        return ResultVoUtil.success(announcementPageDTO);
    }

    @Override
    public ResultVo findByTag(String tag,int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = PageRequest.of(page, size,sort);
        Page<Announcement> announcementPage = announcementRepository.findAllByTag(tag,pageable);
        List<AnnouncementDTO> announcementDTOList = new ArrayList<>();
        announcementPage.getContent().forEach(announcement -> {
            AnnouncementDTO announcementDTO = new AnnouncementDTO(announcement);
            announcementDTOList.add(announcementDTO);
        });
        PageDTO<AnnouncementDTO> announcementPageDTO = new PageDTO<>(announcementDTOList,announcementPage.getTotalElements(),
                (long)announcementPage.getTotalPages());
        return ResultVoUtil.success(announcementPageDTO);
    }

    @Override
    public ResultVo save(String title, String text, String tag) {
        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setText(text);
        announcement.setTag(tag);
        announcementRepository.save(announcement);
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo update(Long id,String title, String text, String tag) {
        Optional<Announcement> announcementOptional = announcementRepository.findById(id);
        if (announcementOptional.isPresent()){
            Announcement announcement = announcementOptional.get();
            announcement.setTitle(title);
            announcement.setText(text);
            announcement.setTag(tag);
            announcementRepository.save(announcement);
            return ResultVoUtil.success();
        }
        return ResultVoUtil.error(0,"发生错误，请稍后再试");
    }

    @Override
    public ResultVo delete(Long id) {
        announcementRepository.deleteById(id);
        return ResultVoUtil.success();
    }
}
