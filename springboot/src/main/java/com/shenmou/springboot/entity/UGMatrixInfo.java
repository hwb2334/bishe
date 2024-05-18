package com.shenmou.springboot.entity;

import java.util.HashMap;
import java.util.Map;

public class UGMatrixInfo {

    private Double[][] ugScore;
    private Integer[][] ugBuyed;
    private Integer[][] ugClicked;
    // 反向索引表
    private Map<Integer, Integer> umap;
    private Map<Integer, Integer> gmap;

    public UGMatrixInfo() {
    }

    public UGMatrixInfo(int uLen, int gLen){
        // 行号为：用户
        // 列好为：商品
        ugScore = new Double[uLen][gLen];
        ugBuyed = new Integer[uLen][gLen];
        ugClicked = new Integer[uLen][gLen];
        umap = new HashMap<>();
        gmap = new HashMap<>();
    }

    public Double[][] getUgScore() {
        return ugScore;
    }

    public void setUgScore(Double[][] ugScore) {
        this.ugScore = ugScore;
    }

    public Integer[][] getUgBuyed() {
        return ugBuyed;
    }

    public void setUgBuyed(Integer[][] ugBuyed) {
        this.ugBuyed = ugBuyed;
    }

    public Integer[][] getUgClicked() {
        return ugClicked;
    }

    public void setUgClicked(Integer[][] ugClicked) {
        this.ugClicked = ugClicked;
    }

    public Map<Integer, Integer> getUmap() {
        return umap;
    }

    public void setUmap(Map<Integer, Integer> umap) {
        this.umap = umap;
    }

    public Map<Integer, Integer> getGmap() {
        return gmap;
    }

    public void setGmap(Map<Integer, Integer> gmap) {
        this.gmap = gmap;
    }
}
