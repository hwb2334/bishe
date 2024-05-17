package com.shenmou.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shenmou.springboot.entity.Orders;

public interface OrdersMapper extends BaseMapper<Orders> {

    Integer insertOrders(Orders orders);
}
