package com.shenmou.springboot.entity.dto;

import com.shenmou.springboot.entity.Mall;
import com.shenmou.springboot.entity.MallToGoods;
import com.shenmou.springboot.entity.RGoods;
import lombok.Data;

import java.util.List;

@Data
public class RGoodsMallDTO {
    private List<RGoods> good;
    private List<Mall> mall;
    // 卖场id ==> [商品id]
    private List<MallToGoods> mallToGoods;
}
