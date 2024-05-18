package com.shenmou.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shenmou.springboot.entity.Normal;

import java.util.List;

// @Mapper
public interface NormalMapper extends BaseMapper<Normal> {

    List<Normal> findByGIds(List<Integer> gIds);
}
