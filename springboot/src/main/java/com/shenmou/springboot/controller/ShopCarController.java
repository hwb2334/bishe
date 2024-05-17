package com.shenmou.springboot.controller;

import com.shenmou.springboot.common.Result;
import com.shenmou.springboot.entity.ShopCar;
import com.shenmou.springboot.entity.dto.GoodMallIdListDTO;
import com.shenmou.springboot.mapper.ShopCarMapper;
import com.shenmou.springboot.service.IShopCarService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/shopCar")
public class ShopCarController {

    @Resource
    private IShopCarService shopCarService;
    @Resource
    private ShopCarMapper shopCarMapper;

    @PostMapping("/save")
    public Result addToShopCar(@RequestBody ShopCar shopCar){
        return Result.success(shopCarService.addOne(shopCar));
    }

    @GetMapping("/find/{userId}")
    public Result findByUserId(@PathVariable Integer userId){
        return Result.success(shopCarService.findByUserId(userId));
    }

    @PostMapping("/find/batch")
    public Result findByIds(@RequestBody GoodMallIdListDTO gmIdListDTO){
        return Result.success(shopCarService.findByIds(gmIdListDTO.getGoodIds(), gmIdListDTO.getMallIds()));
    }

    @PostMapping("/del/{id}")
    public Result delById(@PathVariable Integer id){
        return Result.success(shopCarService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result delBatch(@RequestBody List<Integer> ids){
        return Result.success(shopCarService.removeBatchByIds(ids));
    }
}
