package com.example.multimedia.repository;

import com.example.multimedia.domian.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CookiesEason
 * 2018/09/09 14:02
 */
public interface PictureRepository extends JpaRepository<Picture,Long> {

    Page<Picture> findAllByType(String type, Pageable pageable);

}
