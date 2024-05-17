package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "normal")
public class Normal {
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;
    @TableField(value = "rgoods_id")
    private Integer rgoodsId;
    private Integer maxBuyed;
    private Integer minBuyed;
    private Integer maxClicked;
    private Integer minClicked;
}
