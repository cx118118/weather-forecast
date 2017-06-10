package com.cx.weather;

import com.cx.bean.City;
import com.cx.bean.Province;
import com.cx.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * 在线获取天气预报
 */
public class Main {

    public static List<Province> jsonList;
    public static JFrame frame;

    public static void main(String[] args) throws IOException {

        //初始化首次选择的省份和地区
        jsonList = Utils.loadJSONtoMemory();
        final Vector<String> shengfeng = new Vector<>();//省份信息
        final Vector<String> quyu = new Vector<>();//区域信息
        for (Province province : jsonList) {
            shengfeng.add(province.getName());
        }
        List<City> cityList = jsonList.get(1).getCity();
        for (City city : cityList) {
            quyu.add(city.getName());
        }
        //初始化UI
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Weather frame = new Weather(shengfeng, quyu);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
