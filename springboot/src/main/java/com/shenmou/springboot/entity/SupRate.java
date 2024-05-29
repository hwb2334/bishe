package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName(value = "sup_rate")
public class SupRate {
    @TableId(value = "rating_id",type = IdType.AUTO)
    private Integer ratingId;
    private Integer supId;
    private Float quaRat;
    private Float delRat;
    private Float serRat;
    private LocalDateTime time;
    private Integer userId;
}
