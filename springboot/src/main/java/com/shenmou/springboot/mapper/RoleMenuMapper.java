package com.shenmou.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shenmou.springboot.entity.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    @Delete("delete from role_menu where role_id = #{roleId}")
    void deleteByRoleId(Integer roleId);

    @Select("select menu_id from role_menu where role_id = #{roleId}")
    List<Integer> selectByRoleId(Integer roleId);
}
