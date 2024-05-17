package com.shenmou.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.entity.Banner;
import com.shenmou.springboot.mapper.BannerMapper;
import org.springframework.stereotype.Service;


@Service
public class BannerService extends ServiceImpl<BannerMapper, Banner> {
}
