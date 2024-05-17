package com.shenmou.springboot.controller;

import com.shenmou.springboot.common.Result;
import com.shenmou.springboot.service.IRGoodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rgoods")
public class RGoodsController {

    @Resource
    private IRGoodsService rgoodsService;

    @GetMapping("/lists")
    public Result findDTOAll()
    {
        return Result.success(rgoodsService.findDTOAll());
    }

    @GetMapping("/recommend/{userId}")
    public Result findRecommend(@PathVariable Integer userId){
        return Result.success(rgoodsService.findRecDTO(userId));
    }

    @GetMapping("/{cateId}")
    public Result findDTOByCateId(@PathVariable Integer cateId)
    {
        return Result.success(rgoodsService.findDTOByCateId(cateId));
    }

    @GetMapping("/find/{rgoodsId}")
    public Result findById(@PathVariable Integer rgoodsId)
    {
        return Result.success(rgoodsService.findById(rgoodsId));
    }
}
