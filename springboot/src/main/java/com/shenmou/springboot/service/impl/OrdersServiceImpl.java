package com.shenmou.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.common.Constants;
import com.shenmou.springboot.common.Tool;
import com.shenmou.springboot.entity.*;
import com.shenmou.springboot.entity.dto.OrdeGoodDTO;
import com.shenmou.springboot.entity.dto.OrdersDTO;
import com.shenmou.springboot.mapper.*;
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
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private UserActionsMapper userActionsMapper;

    @Override
    public List<OrdersDTO> findWait(Integer buyerId, String wait) {
        int[] statusCode = Tool.getStatusCode(wait);
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        int len = statusCode.length;
        if (len == 1) {
            queryWrapper.eq("buyer_id", buyerId)
                    .ne("order_status", Constants.STATUS_ORDER_CODE_7)
                    .eq("order_status", statusCode[0]);
        } else if (len == 2) {
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

    private List<OrdersDTO> generateDTOList(List<Orders> list) {
        List<OrdersDTO> dtoList = new ArrayList<>();
        for (Orders order : list) {
            OrdersDTO dto = new OrdersDTO();
            BeanUtil.copyProperties(order, dto, true);
            dto.setStatusName(Tool.getStatusName(dto.getOrderStatus()));
            Integer ordersId = order.getOrdersId();
            Integer mallId = order.getMallId();
            Mall mall = mallMapper.selectById(mallId);
            dto.setMall(mall);
            QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("orders_id", ordersId);
            List<Evaluation> evaluations = evaluationMapper.selectList(queryWrapper);
            List<OrdeGoodDTO> ordeGoodDTOList = new ArrayList<>();
            List<OrdersDetail> details = ordersDetailMapper.findByOrdersId(ordersId);
            int count = 0;
            for (OrdersDetail detail : details) {
                if (!order.getOrderStatus().equals(Constants.STATUS_ORDER_CODE_5) || evalWait(detail.getProductId(), ordersId, evaluations)) {
                    OrdeGoodDTO og = new OrdeGoodDTO();
                    og.setOrdersDetail(detail);
                    RGoods rGoods = rGoodsMapper.selectById(detail.getProductId());
                    og.setRGoods(rGoods);
                    ordeGoodDTOList.add(og);
                    count++;
                }
            }
            if(order.getOrderStatus().equals(Constants.STATUS_ORDER_CODE_5) && count == 0){
                order.setOrderStatus(Constants.STATUS_ORDER_CODE_6);
                addOrders(order);
            }
            dto.setOrdeGoodDTO(ordeGoodDTOList);
            dtoList.add(dto);
        }
        return dtoList;
    }

    private boolean evalWait(Integer productId, Integer ordersId, List<Evaluation> evaluations) {
        return evaluations.stream()
                .noneMatch(eval -> eval.getOrdersId().equals(ordersId) && eval.getRgoodsId().equals(productId));
    }

    @Override
    public Integer addOrders(Orders orders) {
        if (orders.getOrdersId() == null) {
            LocalDateTime now = LocalDateTime.now();
            orders.setCreateTime(now);
            orders.setUpdateTime(now);
            ordersMapper.insertOrders(orders);
            return orders.getOrdersId();
        } else {
            Integer orderStatus = orders.getOrderStatus();
            if(orderStatus.equals(Constants.STATUS_ORDER_CODE_1)){
                Integer ordersId = orders.getOrdersId();
                Integer userId = getById(ordersId).getBuyerId();
                QueryWrapper<OrdersDetail> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("orders_id", ordersId);
                List<OrdersDetail> ods = ordersDetailMapper.selectList(queryWrapper);
                for (OrdersDetail od : ods) {
                    Integer rgoodsId = od.getProductId();
                    // 记录用户购买数量数据
                    QueryWrapper<UserActions> qw1 = new QueryWrapper<>();
                    qw1.eq("user_id", userId);
                    qw1.eq("rgoods_id", rgoodsId);
                    UserActions ua = userActionsMapper.selectOne(qw1);
                    if(ua == null){
                        ua = new UserActions();
                        ua.setUserId(userId);
                        ua.setRgoodsId(rgoodsId);
                        ua.setBuyed(od.getProductNum());
                        ua.setScore(Constants.DEFAULT_ACTION_SCORE);
                        ua.setClicked(0);
                        userActionsMapper.insert(ua);
                    }else{
                        ua.setBuyed(ua.getBuyed() == null ? od.getProductNum() : ua.getBuyed() + od.getProductNum());
                        userActionsMapper.updateById(ua);
                    }
                }
            }
            return ordersMapper.updateById(orders);
        }
    }

    @Override
    public Integer addEvaluation(Evaluation eval) {
        LocalDateTime now = LocalDateTime.now();
        eval.setDate(now);
        Integer userId = eval.getUserId();
        Integer rgoodsId = eval.getRgoodsId();
        // 记录用户评分数据
        QueryWrapper<UserActions> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("rgoods_id", rgoodsId);
        UserActions ua = userActionsMapper.selectOne(queryWrapper);
        if(ua == null){
            ua = new UserActions();
            ua.setUserId(userId);
            ua.setRgoodsId(rgoodsId);
            ua.setScore(eval.getRate().doubleValue());
            ua.setBuyed(0);
            ua.setClicked(0);
            userActionsMapper.insert(ua);
        }else{
            ua.setScore(eval.getRate().doubleValue());
            userActionsMapper.updateById(ua);
        }
        return evaluationMapper.insert(eval);
    }
}
