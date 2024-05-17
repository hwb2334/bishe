package com.shenmou.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shenmou.springboot.entity.OrdersDetail;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrdersDetailMapper extends BaseMapper<OrdersDetail> {

    @Select("select * from orders_detail where orders_id = #{ordersId}")
    List<OrdersDetail> findByOrdersId(Integer ordersId);
}
