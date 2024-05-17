package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user_role")
public class UserRole {
    @TableId(value = "id", type= IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer roleId;

    public UserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
