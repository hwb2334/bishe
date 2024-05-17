package com.shenmou.springboot.entity.dto;

import lombok.Data;

import java.util.List;
@Data
public class GoodMallIdListDTO {
    private List<Integer> goodIds;
    private List<Integer> mallIds;
}
