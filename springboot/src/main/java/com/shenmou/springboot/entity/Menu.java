package com.shenmou.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "menu")
public class Menu {
    @TableId(value = "menu_id",type= IdType.AUTO)
    private Integer menuId;
    private String menuName;
    private String path;
    private String pagePath;
    @TableField(value = "pid")
    private Integer pId;
    private String icon;
    @TableField(exist = false)
    private List<Menu> children;
}
