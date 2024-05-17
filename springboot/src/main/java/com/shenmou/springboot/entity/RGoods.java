package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "rgoods")
public class RGoods {
    @TableId(value = "rgoods_id")
    private Integer rgoodsId;
    private String name;
    private Integer cateId;
    private Integer mallId;
    private Integer num;
    private Float price;
    private LocalDateTime birthDate;
    private LocalDateTime shelfDate;
    private Integer supplierId;
    private String description;
    private String status;
    private String img;
}

