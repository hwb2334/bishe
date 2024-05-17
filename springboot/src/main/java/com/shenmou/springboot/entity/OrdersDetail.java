package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "orders_detail")
public class OrdersDetail {
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;
    private Integer ordersId;
    private Integer productId;
    private Integer productNum;
    private Float allPrice;
}
