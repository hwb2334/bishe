package com.shenmou.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shenmou.springboot.entity.Role;
import org.apache.ibatis.annotations.Select;

public interface RoleMapper extends BaseMapper<Role> {

    @Select("select role_id from role where flag = #{roleFlag}")
    Integer selectByFlag(String roleFlag);

    @Select("select flag from role where role_id = #{roleId}")
    String selectById(Integer roleId);
}
