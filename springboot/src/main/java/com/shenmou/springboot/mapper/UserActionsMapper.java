package com.shenmou.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shenmou.springboot.entity.UserActions;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// @Mapper
public interface UserActionsMapper extends BaseMapper<UserActions> {

    @Select("select * from user_actions")
    List<UserActions> findAll();

    @Select("select * from user_actions where user_id = #{userId}")
    List<UserActions> findByUserId(Integer userId);
}
