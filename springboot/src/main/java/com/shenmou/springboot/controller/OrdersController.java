package com.shenmou.springboot.controller;

import com.shenmou.springboot.common.Result;
import com.shenmou.springboot.entity.Evaluation;
import com.shenmou.springboot.entity.Orders;
import com.shenmou.springboot.entity.OrdersDetail;
import com.shenmou.springboot.mapper.OrdersDetailMapper;
import com.shenmou.springboot.service.IOrdersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Resource
    private IOrdersService ordersService;
    @Resource
    private OrdersDetailMapper ordersDetailMapper;

    @GetMapping("/findWait")
    public Result findWait(@RequestParam Integer buyerId,
                           @RequestParam(defaultValue = "") String wait){
        return Result.success(ordersService.findWait(buyerId, wait));
    }

    // 查询已完成或者订单失效的
    @GetMapping("/findAll/{buyerId}")
    public Result findAll(@PathVariable Integer buyerId){
        return Result.success(ordersService.findByBuyerId(buyerId));
    }

    @PostMapping("/save")
    public Result addOrder(@RequestBody Orders orders){
        return Result.success(ordersService.addOrders(orders));
    }

    @PostMapping("/addDetails")
    public Result addDetails(@RequestBody List<OrdersDetail> ordersDetailList){
        for (OrdersDetail ordersDetail : ordersDetailList) {
            ordersDetailMapper.insert(ordersDetail);
        }
        return Result.success();
    }

    @PostMapping("/evalGood")
    public Result evalGood(@RequestBody Evaluation eval) {
        return Result.success(ordersService.addEvaluation(eval));
    }
}
