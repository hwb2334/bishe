package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "banner")
public class Banner {
    @TableId(value = "banner_id")
    private Integer bannerId;
    private String img;
}

