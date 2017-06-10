package com.cx.util;

import com.alibaba.fastjson.JSON;
import com.cx.bean.City;
import com.cx.bean.Province;
import com.cx.weather.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * 从省市区三级JSON字符串对象中根据省份名称，获取该省份子节点列表
 */
public class Utils {
    public static Vector<String> getAreaListByProvince(List<Province> provinceList, String provinceName) {
        Vector<String> quyu = new Vector<>();
        for (Province province : provinceList) {
            if (province.getName().equals(provinceName)) {
                List<City> cityList = province.getCity();
                for (City city : cityList) {
                    quyu.add(city.getName());
                }
                break;
            }
        }
        return quyu;
    }

    /**
     * 从聚合数据的API接口处加载天气JSON数据
     *
     * @param areaName 要查询的城市
     * @return 返回的天气数据
     */
    public static String loadWeatherJSON(String areaName) {
        String s = null;
        try {
            s = Net.sendGet("http://v.juhe.cn/weather/index",
                    "cityname=" + URLEncoder.encode(areaName, "utf-8")
                            + "&dtype=&format=&key=" + Utils.getConfKey("JUHE-KEY"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 从properties中加载配置
     *
     * @param name
     * @return
     */

    public static String getConfKey(String name) {
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("/conf.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(name);
    }


    /**
     * 从资源中加载JOSN文件，并返回解析好的省市区对象
     * @return
     * @throws IOException
     */

    public static List<Province> loadJSONtoMemory() throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(Main.class.getResourceAsStream("/cities.json"), "utf-8"));
        while (br.ready()) {
            sb.append(br.readLine());
        }
        br.close();
        return new ArrayList<>(JSON.parseArray(sb.toString(), Province.class));
    }
}
