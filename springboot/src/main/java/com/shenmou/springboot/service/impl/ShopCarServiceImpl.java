package com.shenmou.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.entity.Mall;
import com.shenmou.springboot.entity.MallToGoods;
import com.shenmou.springboot.entity.RGoods;
import com.shenmou.springboot.entity.ShopCar;
import com.shenmou.springboot.entity.dto.RGoodsMallDTO;
import com.shenmou.springboot.entity.dto.ShopCarDTO;
import com.shenmou.springboot.mapper.MallMapper;
import com.shenmou.springboot.mapper.RGoodsMapper;
import com.shenmou.springboot.mapper.ShopCarMapper;
import com.shenmou.springboot.service.IShopCarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopCarServiceImpl extends ServiceImpl<ShopCarMapper, ShopCar> implements IShopCarService {

    @Resource
    private ShopCarMapper shopCarMapper;
    @Resource
    private RGoodsMapper rGoodsMapper;
    @Resource
    private MallMapper mallMapper;

    @Override
    public Boolean addOne(ShopCar shopCar) {
        Integer userId = shopCar.getUserId();
        Integer rgoodsId = shopCar.getRgoodsId();
        QueryWrapper<ShopCar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("rgoods_id", rgoodsId);
        ShopCar one = getOne(queryWrapper);
        LocalDateTime now = LocalDateTime.now();
        if(one == null){
            shopCar.setCreatedAt(now);
            shopCar.setUpdatedAt(now);
            shopCar.setStatus(0);
            return save(shopCar);
        }else {
            one.setRgoodsNum(one.getRgoodsNum() + shopCar.getRgoodsNum());
            one.setUpdatedAt(now);
            return updateById(one);
        }
    }

    @Override
    public List<ShopCarDTO> findByUserId(Integer userId) {
        List<ShopCarDTO> shopCarDTOS = shopCarMapper.selectShopCarDTOByUserId(userId);
        for (ShopCarDTO shopCarDTO : shopCarDTOS) {
            shopCarDTO.setMoney(shopCarDTO.getPrice() * shopCarDTO.getRgoodsNum());
        }
        return shopCarDTOS;
    }

    @Override
    public RGoodsMallDTO findByIds(List<Integer> goodIds, List<Integer> mallIds) {
        RGoodsMallDTO rGoodsMallDTO = new RGoodsMallDTO();
        if(goodIds.size() == 0) return null;
        List<RGoods> rGoods = rGoodsMapper.selectBatchIds(goodIds);
        List<Mall> malls = mallMapper.selectBatchIds(mallIds);
        rGoodsMallDTO.setGood(rGoods);
        rGoodsMallDTO.setMall(malls);
        List<MallToGoods> mallToGoods = new ArrayList<>();
        for (Mall mall : malls) {
            MallToGoods mtg = new MallToGoods();
            mtg.setMallId(mall.getMallId());
            mtg.setIds(rGoods.stream()
                    .filter(good -> good.getMallId().equals(mall.getMallId()))
                    .map(RGoods::getRgoodsId).collect(Collectors.toList()));
            mallToGoods.add(mtg);
        }
        rGoodsMallDTO.setMallToGoods(mallToGoods);
        return rGoodsMallDTO;
    }
}
