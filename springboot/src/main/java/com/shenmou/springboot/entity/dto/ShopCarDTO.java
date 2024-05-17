package com.shenmou.springboot.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class ShopCarDTO {
    private Integer id;
    @TableField(value = "rgoods_num")
    private Integer rgoodsNum;
    private Float price;
    @TableField(value = "rgoods_id")
    private Integer rgoodsId;
    // 商品名字
    private String name;
    private Integer mallId;
    private String mallName;
    private String img;
    private Float money;
}
