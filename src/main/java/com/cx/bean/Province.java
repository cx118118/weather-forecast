package com.cx.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON对象   省份
 */
public class Province {
    private String name;

    private List<City> city = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }
}
