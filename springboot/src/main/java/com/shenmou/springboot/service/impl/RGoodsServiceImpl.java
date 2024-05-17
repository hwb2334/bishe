package com.shenmou.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.entity.Mall;
import com.shenmou.springboot.entity.MallToGoods;
import com.shenmou.springboot.entity.RGoods;
import com.shenmou.springboot.entity.UserActions;
import com.shenmou.springboot.entity.dto.RGoodsDTO;
import com.shenmou.springboot.entity.dto.RGoodsMallDTO;
import com.shenmou.springboot.mapper.MallMapper;
import com.shenmou.springboot.mapper.RGoodsMapper;
import com.shenmou.springboot.mapper.UserActionsMapper;
import com.shenmou.springboot.service.IRGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RGoodsServiceImpl extends ServiceImpl<RGoodsMapper, RGoods> implements IRGoodsService {

    @Resource
    private MallMapper mallMapper;
    @Resource
    private UserActionsMapper userActionsMapper;

    @Override
    public List<RGoodsDTO> findDTOAll() {
        List<RGoods> rGoods = list();
        return change(rGoods);
    }

    @Override
    public List<RGoodsDTO> findDTOByCateId(Integer cateId) {
        QueryWrapper<RGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cate_id", cateId);
        List<RGoods> rGoods = list(queryWrapper);
        return change(rGoods);
    }

    @Override
    public RGoodsMallDTO findById(Integer rgoodsId) {
        RGoodsMallDTO rGoodsMallDTO = new RGoodsMallDTO();
        QueryWrapper<RGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rgoods_id", rgoodsId);
        RGoods good = getOne(queryWrapper);
        Mall mall = mallMapper.selectById(good.getMallId());
        rGoodsMallDTO.setGood(Collections.singletonList(good));
        rGoodsMallDTO.setMall(Collections.singletonList(mall));
        MallToGoods mallToGoods = new MallToGoods();
        mallToGoods.setMallId(mall.getMallId());
        mallToGoods.setIds(Collections.singletonList(good.getRgoodsId()));
        rGoodsMallDTO.setMallToGoods(Collections.singletonList(mallToGoods));
        return rGoodsMallDTO;
    }

    @Override
    public List<RGoodsDTO> findRecDTO(Integer userId) {
        // 当前用户评价信息
        List<UserActions> curActions = userActionsMapper.findByUserId(userId);
        // 已经评分的商品Id数组
        Integer[] idsDone = curActions.stream().map(UserActions::getRgoodsId).toArray(Integer[]::new);
        List<RGoods> list = list();
        // 未评分的商品Id数组
        Integer[] idsWait = list.stream()
                .filter(good -> Arrays.stream(idsDone).noneMatch(id -> id.equals(good.getRgoodsId())))
                .collect(Collectors.toList())
                .stream()
                .map(RGoods::getRgoodsId)
                .toArray(Integer[]::new);
        return null;
    }

    private List<RGoodsDTO> change(List<RGoods> rGoods){
        List<RGoodsDTO> rGoodsDTOS = new ArrayList<>();
        for (RGoods rGood : rGoods) {
            RGoodsDTO rGoodsDTO = new RGoodsDTO();
            BeanUtil.copyProperties(rGood, rGoodsDTO, true);
            rGoodsDTOS.add(rGoodsDTO);
        }
        return rGoodsDTOS;
    }

    // 构建用户商品矩阵
//    private void
}
