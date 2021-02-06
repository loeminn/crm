package com.loemin.crm.utils;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public class PrintJson {

    //将boolean值解析为json串
    public static void printJsonFlag(HttpServletResponse response, boolean flag) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", flag);
        printJsonObj(response, map);
    }

    //将对象解析为json串
    public static void printJsonObj(HttpServletResponse response, Object obj) {
        String json = JSON.toJSONString(obj);
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}























