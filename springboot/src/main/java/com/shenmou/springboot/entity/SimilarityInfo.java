package com.shenmou.springboot.entity;

import java.util.Map;

public class SimilarityInfo {

    private Double[] simi;
    private Map<Integer, Integer> gmap;
    private Integer len;

    public SimilarityInfo() {
    }

    public Integer getIndex(int row, int col){
        return row > col ? (2*len-col+1)*col/2+row-col : (2*len-row+1)*row/2+col-row;
    }

    public SimilarityInfo(int gLen, int len){
        simi = new Double[gLen];
        this.len = len;
    }

    public Double[] getSimi() {
        return simi;
    }

    public void setSimi(Double[] simi) {
        this.simi = simi;
    }

    public Map<Integer, Integer> getGmap() {
        return gmap;
    }

    public void setGmap(Map<Integer, Integer> gmap) {
        this.gmap = gmap;
    }
}
