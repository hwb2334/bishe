package com.shenmou.springboot.controller;

import com.shenmou.springboot.common.Result;
import com.shenmou.springboot.service.IRGoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rgoods")
public class RGoodsController {

    @Resource
    private IRGoodsService rgoodsService;

    @GetMapping("/lists")
    public Result findDTOAll(@RequestParam(defaultValue = "") String curAddress)
    {
        return Result.success(rgoodsService.findDTOAll(curAddress));
    }

    @GetMapping("/recommend/{userId}")
    public Result findRecommend(@PathVariable Integer userId, @RequestParam(defaultValue = "") String curAddress){
        return Result.success(rgoodsService.findRecDTO(userId, curAddress));
    }

    @GetMapping("/{cateId}")
    public Result findDTOByCateId(@PathVariable Integer cateId, @RequestParam(defaultValue = "") String curAddress)
    {
        return Result.success(rgoodsService.findDTOByCateId(cateId, curAddress));
    }

    @GetMapping("/find/{rgoodsId}")
    public Result findById(@PathVariable Integer rgoodsId, @RequestParam Integer userId)
    {
        return Result.success(rgoodsService.findById(rgoodsId, userId));
    }

    @GetMapping("/findName")
    public Result findGoodName(@RequestParam(defaultValue = "") String rgoodsName, @RequestParam(defaultValue = "") String curAddress){
        return Result.success(rgoodsService.findByName(rgoodsName, curAddress));
    }
}
