package com.shenmou.springboot.common;

public class Tool {

    public static String getStatusName(Integer code){
        if(code.equals(Constants.STATUS_ORDER_CODE_0))
            return "待付款";
        else if(code.equals(Constants.STATUS_ORDER_CODE_1) || code.equals(Constants.STATUS_ORDER_CODE_2))
            return "待发货";
        else if(code.equals(Constants.STATUS_ORDER_CODE_3))
            return "配送中";
        else if(code.equals(Constants.STATUS_ORDER_CODE_4))
            return "待取货";
        else if(code.equals(Constants.STATUS_ORDER_CODE_5))
            return "待评价";
        else if(code.equals(Constants.STATUS_ORDER_CODE_6))
            return "完成";
        else if(code.equals(Constants.STATUS_ORDER_CODE_7))
            return "订单失效";
        else return "";
    }

    public static int[] getStatusCode(String name){
        if(name.equals("待付款")){
            return new int[]{Constants.STATUS_ORDER_CODE_0};
        }else if(name.equals("待发货")){
            return new int[]{Constants.STATUS_ORDER_CODE_1,Constants.STATUS_ORDER_CODE_2};
        }else if(name.equals("配送中")){
            return new int[]{Constants.STATUS_ORDER_CODE_3};
        }else if(name.equals("待取货")){
            return new int[]{Constants.STATUS_ORDER_CODE_4};
        }else if(name.equals("待评价")){
            return new int[]{Constants.STATUS_ORDER_CODE_5};
        }else if(name.equals("完成")){
            return new int[]{Constants.STATUS_ORDER_CODE_6};
        }else if(name.equals("订单失效")){
            return new int[]{Constants.STATUS_ORDER_CODE_7};
        }else return new int[]{};
    }
}
