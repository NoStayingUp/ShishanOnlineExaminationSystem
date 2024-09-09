package com.feidian.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.stream.Collectors;

public class JSONUtil {

    public static List<Integer> jsonStrToList(String jsonStr) {
        //首先将json字符串转换为string列表
        List<String> stringList = JSON.parseArray(jsonStr,String.class);
        // 然后，将List<String>转换为List<Integer>
//        List<Integer> integerList = new ArrayList<>();
//        for (String s : stringList) {
//            integerList.add(Integer.parseInt(s));
//        }

        // 或者，使用Java 8的Stream API进行转换（更简洁）
        List<Integer> integerList = stringList.stream().map(Integer::parseInt).collect(Collectors.toList());


        return integerList;
    }
}
