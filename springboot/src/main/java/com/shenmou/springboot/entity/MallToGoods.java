package com.shenmou.springboot.entity;

import lombok.Data;

import java.util.List;

@Data
public class MallToGoods {

    private Integer mallId;
    private List<Integer> ids;
}
