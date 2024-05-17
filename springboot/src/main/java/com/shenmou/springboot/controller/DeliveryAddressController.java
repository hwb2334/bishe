package com.shenmou.springboot.controller;

import com.shenmou.springboot.common.Result;
import com.shenmou.springboot.entity.DeliveryAddress;
import com.shenmou.springboot.service.IDeliveryAddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/deliAdd")
public class DeliveryAddressController {

    @Resource
    private IDeliveryAddressService deliveryAddressService;

    @PostMapping("/save")
    public Result addOne(@RequestBody DeliveryAddress deliveryAddress){
        return Result.success(deliveryAddressService.addOne(deliveryAddress));
    }

    @PostMapping("/delete/{id}")
    public Result del(@PathVariable Integer id){
        return Result.success(deliveryAddressService.del(id));
    }

    @GetMapping("/findAll/{userId}")
    public Result findAll(@PathVariable Integer userId){
        return Result.success(deliveryAddressService.findAll(userId));
    }
}
