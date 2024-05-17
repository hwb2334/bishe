package com.shenmou.springboot.entity.dto;

import com.shenmou.springboot.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserRolesDTO {

    private List<Role> checked;
    private List<Role> unChecked;
}
