package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user_actions")
public class UserActions {
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;
    private Integer userId;
    @TableField(value = "rgoods_id")
    private Integer rgoodsId;
    private Float score;
    private Integer buyed;
    private Integer clicked;
}
