package com.shenmou.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shenmou.springboot.entity.RGoods;
import com.shenmou.springboot.entity.dto.RGoodsDTO;
import com.shenmou.springboot.entity.dto.RGoodsMallDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IRGoodsService extends IService<RGoods> {
    List<RGoodsDTO> findDTOAll(String curAddress);

    List<RGoodsDTO> findDTOByCateId(Integer cateId, String curAddress);

    RGoodsMallDTO findById(Integer rgoodsId, Integer userId);

    List<RGoodsDTO> findRecDTO(Integer userId, String curAddress);

    List<RGoodsDTO> findByName(String goodName, String curAddress);
}
