package com.shenmou.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shenmou.springboot.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends BaseMapper<User> {

    @Update("update user set default_deli = #{deliId} where user_id = #{userId}")
    Boolean updateDefaultDeli(@Param("userId") Integer userId,@Param("deliId") Integer deliId);
}
