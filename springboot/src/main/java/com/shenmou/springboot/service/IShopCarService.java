package com.shenmou.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shenmou.springboot.entity.ShopCar;
import com.shenmou.springboot.entity.dto.RGoodsMallDTO;
import com.shenmou.springboot.entity.dto.ShopCarDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IShopCarService extends IService<ShopCar> {
    Boolean addOne(ShopCar shopCar);

    List<ShopCarDTO> findByUserId(Integer userId);

    RGoodsMallDTO findByIds(List<Integer> goodIds, List<Integer> mallIds);
}
