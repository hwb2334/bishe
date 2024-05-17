package com.shenmou.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.entity.Role;
import com.shenmou.springboot.mapper.RoleMapper;
import com.shenmou.springboot.service.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
