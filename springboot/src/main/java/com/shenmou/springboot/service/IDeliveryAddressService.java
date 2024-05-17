package com.shenmou.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shenmou.springboot.entity.DeliveryAddress;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IDeliveryAddressService extends IService<DeliveryAddress> {
    List<DeliveryAddress> findAll(Integer userId);

    Boolean del(Integer id);

    Integer addOne(DeliveryAddress deliveryAddress);
}
