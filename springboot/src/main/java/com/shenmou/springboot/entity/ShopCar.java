package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "shop_car")
public class ShopCar {

    @TableId(value = "id", type= IdType.AUTO)
    private Integer id;
    private Integer userId;
    @TableField(value = "rgoods_id")
    private Integer rgoodsId;
    private Float price;
    @TableField(value = "rgoods_num")
    private Integer rgoodsNum;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
