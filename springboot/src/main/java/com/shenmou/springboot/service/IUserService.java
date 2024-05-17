package com.shenmou.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shenmou.springboot.entity.User;
import com.shenmou.springboot.entity.dto.UserDTO;
import org.springframework.stereotype.Service;


@Service
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void saveUser(User user);

    UserDTO updateInfo(UserDTO userDTO);
}
