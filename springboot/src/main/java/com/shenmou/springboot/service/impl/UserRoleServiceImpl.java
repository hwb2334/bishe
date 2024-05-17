package com.shenmou.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.entity.UserRole;
import com.shenmou.springboot.mapper.UserRoleMapper;
import com.shenmou.springboot.service.IUserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
