package com.example.multimedia.dto;

import lombok.Data;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/09/16 13:14
 */
@Data
public class SimpleUserWorkDTO {

    private SimpleUserDTO simpleUserDTO;

    private  List<Object[]> simpleWorkDTOs;

}
