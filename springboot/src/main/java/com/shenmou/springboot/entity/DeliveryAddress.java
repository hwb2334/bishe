package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "delivery_address")
public class DeliveryAddress {
    @TableId(value = "id")
    private Integer id;
    private Integer userId;
    private String info;
    private String details;
    private String name;
    private String phone;
}
