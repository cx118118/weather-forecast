package com.cx.weather;

import com.alibaba.fastjson.JSON;
import com.cx.bean.Today;
import com.cx.bean.WeatherObj;
import com.cx.util.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

/**
 * 天气查询程序主界面
 */
public class Weather extends JFrame {

    private JComboBox<String> comboBox_qy;
    private JComboBox<String> comboBox_sf;
    private String areaName;
    private JLabel img_label;

    /**
     * 创建Frame
     */
    public Weather(Vector<String> shengfeng, Vector<String> quyu) {

        img_label = new JLabel("");
        setTitle("\u5929\u6C14\u67E5\u8BE2\u7A0B\u5E8F");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(null);

        JTextArea msg = new JTextArea();
        msg.setEditable(false);
        msg.setBounds(32, 126, 243, 106);
        getContentPane().add(msg);
        msg.setLineWrap(true);

        comboBox_sf = new JComboBox<String>();
        comboBox_sf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Vector<String> areaListByProvince = Utils.getAreaListByProvince(Main.jsonList,
                        comboBox_sf.getSelectedItem().toString());
                comboBox_qy.setModel(new DefaultComboBoxModel<>(areaListByProvince));
                areaName = areaListByProvince.get(0);
            }
        });
        comboBox_sf.setModel(new DefaultComboBoxModel<>(shengfeng));
        comboBox_sf.setBounds(96, 25, 102, 21);
        getContentPane().add(comboBox_sf);

        comboBox_qy = new JComboBox<String>();
        if (areaName == null && quyu.size() >= 1) {
            areaName = quyu.get(0);
        }
        comboBox_qy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                areaName = comboBox_qy.getSelectedItem().toString();
            }
        });
        comboBox_qy.setModel(new DefaultComboBoxModel<>(quyu));
        comboBox_qy.setBounds(285, 25, 102, 21);
        getContentPane().add(comboBox_qy);

        JLabel sf_Label = new JLabel("\u7701\u4EFD");
        sf_Label.setBounds(32, 28, 54, 15);
        getContentPane().add(sf_Label);

        JLabel qy_Label = new JLabel("\u533A\u57DF");
        qy_Label.setBounds(221, 28, 54, 15);
        getContentPane().add(qy_Label);

        JButton search = new JButton("\u67E5\u8BE2\u5929\u6C14");
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (areaName != null) {
                    String s = Utils.loadWeatherJSON(areaName);
                    WeatherObj weatherObj = JSON.parseObject(s, WeatherObj.class);
                    Today today = weatherObj.getResult().getToday();
                    StringBuffer sb = new StringBuffer();
                    sb.append("地区：").append(today.getCity()).append("\r\n");
                    sb.append("温度：").append(today.getTemperature()).append("\r\n");
                    sb.append("天气：").append(today.getWeather()).append("\r\n");
                    sb.append("提示：").append(today.getDressing_advice());
                    msg.setText(sb.toString());
                    // 设置天气图标
                    String weatherName = today.getWeather();
                    if (weatherName != null) {
                        if (weatherName.equals("晴")) {
                            setLabelImg("晴");
                        } else if (weatherName.contains("多云")) {
                            setLabelImg("多云");
                        } else if (weatherName.contains("阴")) {
                            setLabelImg("阴");
                        } else if (weatherName.contains("雨")) {
                            setLabelImg("雨");
                        } else if (weatherName.contains("雪")) {
                            setLabelImg("雪");
                        } else {
                            img_label.setIcon(null);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(Main.frame, "请选择区域", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        search.setBounds(285, 56, 102, 23);
        getContentPane().add(search);
    }

    private void setLabelImg(String name) {
        img_label.setBounds(285, 126, 102, 106);
        getContentPane().add(img_label);
        Image image = null; //
        try {
            image = ImageIO.read(Main.class.getResourceAsStream(name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 先取出Image ,然后缩放到合适的大小
        Image smallImage = image.getScaledInstance(img_label.getHeight(), img_label.getWidth(), Image.SCALE_FAST);
        // 再由修改后的Image来生成合适的Icon
        ImageIcon smallIcon = new ImageIcon(smallImage);
        img_label.setIcon(smallIcon);
    }

}
