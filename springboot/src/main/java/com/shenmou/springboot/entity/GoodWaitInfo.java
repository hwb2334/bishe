package com.shenmou.springboot.entity;

import java.util.*;
import java.util.stream.Collectors;

public class GoodWaitInfo {

    private Double[] scoreWait;
    private Integer[] idsWait;
    private Integer len;
    private Integer size;

    public GoodWaitInfo(Double[] scoreWait, Integer[] idsWait) {
        this.scoreWait = scoreWait;
        this.idsWait = idsWait;
        len = scoreWait.length;
        size = scoreWait.length;
    }

    // 选出未评分的商品中前nums个商品
    public List<RGoods> getRec(Integer nums, List<RGoods> goods) {
        Map<Integer, RGoods> goodsMap = new HashMap<>();
        for (RGoods good : goods) {
            goodsMap.put(good.getRgoodsId(), good);
        }
        List<RGoods> rst = new ArrayList<>();
        if (nums > len) {
            for (Integer id : idsWait) {
                rst.add(goodsMap.get(id));
            }
        } else {
            // 构建堆
            init();
            for(int i = 0;i<nums;i++){
                Integer id = idsWait[i];
                rst.add(goodsMap.get(id));
                swap(0, --size);
                adjust(0);
            }
        }
        return rst;
    }

    private void init() {
        for(int mid = size / 2 - 1;mid>=0;mid--) {
            adjust(mid);
        }
    }

    private void adjust(int k){
        Double cur = scoreWait[k];
        for(int i = 2*k; i<size; i*=2){
            if(i+1<len && scoreWait[i+1]>scoreWait[i]){
                i+=1;
            }
            if(scoreWait[i] < cur){
                break;
            }else{
                swap(k, i);
                k = i;
                cur = scoreWait[k];
            }
        }
    }

    private void swap(Integer i1, Integer i2) {
        Integer idTmp = idsWait[i1];
        idsWait[i1] = idsWait[i2];
        idsWait[i2] = idTmp;
        Double scoreTmp = scoreWait[i1];
        scoreWait[i1] = scoreWait[i2];
        scoreWait[i2] = scoreTmp;
    }
}
