package com.cx.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON对象   城市
 */
public class City {
    private String name;
    private List<String> area = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArea() {
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }
}
