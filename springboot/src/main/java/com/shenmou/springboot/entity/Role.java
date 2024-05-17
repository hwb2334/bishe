package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "role")
public class Role {
    @TableId(value = "role_id")
    private Integer roleId;
    private String roleName;
    private String flag;
}
