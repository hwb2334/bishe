package com.shenmou.springboot.entity.dto;

import com.shenmou.springboot.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Integer userId;
    private String userName;
    private String password;
    private String token;
    private String avatar;
    private String address;
    private String phone;
    private String email;
    private Integer roleId;
    private String roleFlag;
    private Integer defaultDeli;
    private List<Menu> menus;
}
