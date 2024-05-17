package com.shenmou.springboot.entity.dto;

import com.shenmou.springboot.entity.Mall;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdersDTO {

    private Integer ordersId;
    private Float totalPrice;
    private Integer orderStatus;
    private String statusName;
    private Integer deliveryMethod;
    private String deliveryAddress;
    private String notes;
    private LocalDateTime createTime;
    private LocalDateTime shiptime;
    private LocalDateTime arrtime;
    // mall
    private Mall mall;
    // OrdersDetail <--> RGoods
    private List<OrdeGoodDTO> ordeGoodDTO;
}
