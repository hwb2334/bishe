package com.shenmou.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.entity.DeliveryAddress;
import com.shenmou.springboot.mapper.DeliveryAddressMapper;
import com.shenmou.springboot.mapper.UserMapper;
import com.shenmou.springboot.service.IDeliveryAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeliveryAddressServiceImpl extends ServiceImpl<DeliveryAddressMapper, DeliveryAddress> implements IDeliveryAddressService {

    @Resource
    private UserMapper userMapper;
    @Override
    public List<DeliveryAddress> findAll(Integer userId) {
        QueryWrapper<DeliveryAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return list(queryWrapper);
    }

    @Override
    public Boolean del(Integer id) {
        return removeById(id);
    }

    @Override
    public Integer addOne(DeliveryAddress deliveryAddress) {
        Integer userId = deliveryAddress.getUserId();
        Integer id = deliveryAddress.getId();
        if(id == null){
            return add(userId, deliveryAddress);
        }
        else{
            return update(userId, deliveryAddress);
        }
    }

    private Integer update(Integer userId, DeliveryAddress deliveryAddress){
        saveOrUpdate(deliveryAddress);
        userMapper.updateDefaultDeli(userId, deliveryAddress.getId());
        return deliveryAddress.getId();
    }

    private Integer add(Integer userId, DeliveryAddress deliveryAddress){
        QueryWrapper<DeliveryAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", deliveryAddress.getUserId());
        queryWrapper.eq("info", deliveryAddress.getInfo());
        queryWrapper.eq("details", deliveryAddress.getDetails());
        queryWrapper.eq("name", deliveryAddress.getName());
        queryWrapper.eq("phone", deliveryAddress.getPhone());
        DeliveryAddress one = getOne(queryWrapper);
        if(one == null){
            save(deliveryAddress);
            one = getOne(queryWrapper);
        }
        userMapper.updateDefaultDeli(userId, one.getId());
        return one.getId();
    }
}
