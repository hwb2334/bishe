package com.shenmou.springboot.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shenmou.springboot.common.Constants;
import com.shenmou.springboot.entity.Orders;
import com.shenmou.springboot.entity.OrdersDetail;
import com.shenmou.springboot.mapper.OrdersDetailMapper;
import com.shenmou.springboot.mapper.OrdersMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class OrderExpirationTask {

    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private OrdersDetailMapper ordersDetailMapper;

    @Scheduled(fixedRate = 24 * 60 * 60000) // 每分钟执行一次
    public void checkOrderExpiration(){
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_status", Constants.STATUS_ORDER_CODE_0);
        List<Orders> orders = ordersMapper.selectList(queryWrapper);
        for (Orders order : orders) {
            LocalDateTime createTime = order.getCreateTime();
            LocalDateTime updateTime = LocalDateTime.now();
            long daysBetween = ChronoUnit.HOURS.between(createTime, updateTime);
            if(daysBetween > Constants.ORDER_WAIT_PAY_LIMIT_HOUR){
                order.setOrderStatus(Constants.STATUS_ORDER_CODE_7);
                Integer ordersId = order.getOrdersId();
                List<OrdersDetail> ods = ordersDetailMapper.findByOrdersId(ordersId);
                for (OrdersDetail od : ods) {
                    ordersDetailMapper.deleteById(od);
                }
            }
            ordersMapper.updateById(order);
        }
    }
}
