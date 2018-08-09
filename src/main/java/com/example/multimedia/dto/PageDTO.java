package com.example.multimedia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/05 22:40
 */
@Data
public class PageDTO<T> {

    private List<T> objectList;

    private Long totalElements;

    private Long totalPages;

    public PageDTO(List<T> objectList, Long totalElements, Long totalPages) {
        this.objectList = objectList;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
