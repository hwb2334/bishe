package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "mall")
public class Mall {
    @TableId(value = "mall_id")
    private Integer mallId;
    private String mallName;
    private String contactMan;
    private Integer contactNum;
    private String address;
    private String status;
    private String notes;
}
