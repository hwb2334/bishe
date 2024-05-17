package com.shenmou.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shenmou.springboot.entity.ShopCar;
import com.shenmou.springboot.entity.dto.ShopCarDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ShopCarMapper extends BaseMapper<ShopCar> {

    @Select("select shop_car.id, rgoods_num, shop_car.price, rgoods.img, shop_car.rgoods_id, name, rgoods.mall_id, mall_name from shop_car " +
            "left join rgoods on shop_car.rgoods_id = rgoods.rgoods_id " +
            "left join mall on rgoods.mall_id = mall.mall_id " +
            "where shop_car.user_id = #{userId} and shop_car.status = 0")
    List<ShopCarDTO> selectShopCarDTOByUserId(Integer userId);
}
