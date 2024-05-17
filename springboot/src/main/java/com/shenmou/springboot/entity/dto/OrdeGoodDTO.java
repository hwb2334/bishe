package com.shenmou.springboot.entity.dto;

import com.shenmou.springboot.entity.OrdersDetail;
import com.shenmou.springboot.entity.RGoods;
import lombok.Data;

@Data
public class OrdeGoodDTO {

    private OrdersDetail ordersDetail;
    private RGoods rGoods;
}
