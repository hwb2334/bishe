package com.shenmou.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.common.Constants;
import com.shenmou.springboot.common.Tool;
import com.shenmou.springboot.entity.Mall;
import com.shenmou.springboot.entity.Orders;
import com.shenmou.springboot.entity.OrdersDetail;
import com.shenmou.springboot.entity.RGoods;
import com.shenmou.springboot.entity.dto.OrdeGoodDTO;
import com.shenmou.springboot.entity.dto.OrdersDTO;
import com.shenmou.springboot.mapper.MallMapper;
import com.shenmou.springboot.mapper.OrdersDetailMapper;
import com.shenmou.springboot.mapper.OrdersMapper;
import com.shenmou.springboot.mapper.RGoodsMapper;
import com.shenmou.springboot.service.IOrdersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private MallMapper mallMapper;
    @Resource
    private OrdersDetailMapper ordersDetailMapper;
    @Resource
    private RGoodsMapper rGoodsMapper;

    @Override
    public List<OrdersDTO> findWait(Integer buyerId, String wait) {
        int[] statusCode = Tool.getStatusCode(wait);
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        int len = statusCode.length;
        if(len == 1){
            queryWrapper.eq("buyer_id", buyerId)
                    .ne("order_status", Constants.STATUS_ORDER_CODE_7)
                    .eq("order_status", statusCode[0]);
        }else if(len == 2){
            queryWrapper.eq("buyer_id", buyerId)
                    .ne("order_status", Constants.STATUS_ORDER_CODE_7)
                    .eq("order_status", statusCode[0])
                    .or().eq("order_status", statusCode[1]);
        }
        List<Orders> list = list(queryWrapper);
        return generateDTOList(list);
    }

    @Override
    public List<OrdersDTO> findByBuyerId(Integer buyerId) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyer_id", buyerId)
                .eq("order_status", Constants.STATUS_ORDER_CODE_7)
                .or().eq("order_status", Constants.STATUS_ORDER_CODE_6);
        List<Orders> list = list(queryWrapper);
        return generateDTOList(list);
    }

    private List<OrdersDTO> generateDTOList(List<Orders> list){
        List<OrdersDTO> dtoList = new ArrayList<>();
        for (Orders order : list) {
            OrdersDTO dto = new OrdersDTO();
            BeanUtil.copyProperties(order, dto, true);
            dto.setStatusName(Tool.getStatusName(dto.getOrderStatus()));
            Integer ordersId = order.getOrdersId();
            Integer mallId = order.getMallId();
            Mall mall = mallMapper.selectById(mallId);
            dto.setMall(mall);
            List<OrdeGoodDTO> ordeGoodDTOList = new ArrayList<>();
            List<OrdersDetail> details = ordersDetailMapper.findByOrdersId(ordersId);
            for (OrdersDetail detail : details) {
                OrdeGoodDTO og = new OrdeGoodDTO();
                og.setOrdersDetail(detail);
                RGoods rGoods = rGoodsMapper.selectById(detail.getProductId());
                og.setRGoods(rGoods);
                ordeGoodDTOList.add(og);
            }
            dto.setOrdeGoodDTO(ordeGoodDTOList);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public Integer addOrders(Orders orders) {
        if(orders.getOrdersId() == null){
            LocalDateTime now  = LocalDateTime.now();
            orders.setCreateTime(now);
            orders.setUpdateTime(now);
            ordersMapper.insertOrders(orders);
            return orders.getOrdersId();
        }else{
            return ordersMapper.updateById(orders);
        }
    }
}
