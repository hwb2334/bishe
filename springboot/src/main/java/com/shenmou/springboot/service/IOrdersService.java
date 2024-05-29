package com.shenmou.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shenmou.springboot.common.Result;
import com.shenmou.springboot.entity.Evaluation;
import com.shenmou.springboot.entity.Orders;
import com.shenmou.springboot.entity.dto.OrdersDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IOrdersService extends IService<Orders> {


    List<OrdersDTO> findWait(Integer buyerId, String wait);

    List<OrdersDTO> findByBuyerId(Integer buyerId);

    Integer addOrders(Orders orders);

    Integer addEvaluation(Evaluation eval);
}
