package com.shenmou.springboot.common;

public interface Constants {
    String CODE_200 = "200"; // 成功
    String CODE_500 = "500"; // 系统错误
    String CODE_401 = "401"; // 权限不足
    String CODE_400 = "400"; // 参数错误
    String CODE_600 = "600"; // 其他业务异常
    String AVATAR_DEFAULT = "http://img.alicdn.com/sns_logo/TB1e4rMt8Bh1e4jSZFhXXcC9VXa-240-240.png";
    String DICT_TYPE_ICON = "icon";

    long ORDER_WAIT_PAY_LIMIT_HOUR = 5l;

    Integer STATUS_ORDER_CODE_0 = 0; //待付款
    Integer STATUS_ORDER_CODE_1 = 1; //待接单
    Integer STATUS_ORDER_CODE_2 = 2; //待取单
    Integer STATUS_ORDER_CODE_3 = 3; //配送中
    Integer STATUS_ORDER_CODE_4 = 4; //配送已完成,用户待取货
    Integer STATUS_ORDER_CODE_5 = 5; //用户待评价
    Integer STATUS_ORDER_CODE_6 = 6; //评价完成
    Integer STATUS_ORDER_CODE_7 = 7; //订单失效

    Integer SCORE_WAY = 5;
    Integer RECOMMAND_NUMS = 5;
}
