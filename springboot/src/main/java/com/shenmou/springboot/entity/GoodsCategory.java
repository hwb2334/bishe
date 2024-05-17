package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "goods_category")
public class GoodsCategory {

    @TableId(value = "cate_id")
    private Integer cateId;
    private String cateName;
    private String description;
}
