package com.shenmou.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.entity.Dict;
import com.shenmou.springboot.mapper.DictMapper;
import com.shenmou.springboot.service.IDictService;
import org.springframework.stereotype.Service;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

}
