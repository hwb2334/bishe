package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "orders")
public class Orders {
    @TableId(value = "orders_id",type= IdType.AUTO)
    private Integer ordersId;
    private Integer buyerId;
    private Float totalPrice;
    private Integer orderStatus;
    private Integer mallId;
    private Integer deliveryMethod;
    private Integer sendmanId;
    private String paymentMethod;
    private String deliveryAddress;
    private String notes;
    private String originPlace;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime shiptime;
    private LocalDateTime arrtime;
}
