package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@TableName(value = "user")
public class User {
    @TableId(value = "user_id",type= IdType.AUTO)
    private Integer userId;
    private String userName;
    @JsonIgnore
    private String password;
    private String address;
    private String phone;
    private String avatar;
    private Integer roleId;
    private String roleFlag;
    @TableField(value = "email") // 指定数据库的字段名称
    private String email;
    private Integer defaultDeli;
}
