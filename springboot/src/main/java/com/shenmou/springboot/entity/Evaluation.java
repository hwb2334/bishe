package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "evaluation")
public class Evaluation {
    @TableId(value = "eva_id",type= IdType.AUTO)
    private Integer evaId;
    @TableField(value = "rgoods_id")
    private Integer rgoodsId;
    private String response;
    private Integer userId;
    private Float rate;
    private Integer storemanId;
    private LocalDateTime date;
    private String review;
    private String status;
}
