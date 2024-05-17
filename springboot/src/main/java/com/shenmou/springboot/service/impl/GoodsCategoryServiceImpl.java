package com.shenmou.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.entity.GoodsCategory;
import com.shenmou.springboot.mapper.GoodsCategoryMapper;
import com.shenmou.springboot.service.IGoodsCategoryService;
import org.springframework.stereotype.Service;

@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements IGoodsCategoryService {

}
