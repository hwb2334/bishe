package com.shenmou.springboot.entity;

import java.util.ArrayList;
import java.util.List;

public class GUS {

    private Integer gid;
    private List<Integer> uid;

    public GUS(){
        uid = new ArrayList<>();
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public List<Integer> getUid() {
        return uid;
    }

    public void setUid(List<Integer> uid) {
        this.uid = uid;
    }
}
