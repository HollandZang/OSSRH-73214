package com.holland.http.demo;

import com.holland.http.ZnHttpUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SyncGet {

    public static void main(String[] args) {
        final Map<String, Object> data = new HashMap<>();
        data.put("t1", LocalDateTime.now());
        data.put("t2", LocalTime.now());
        data.put("t3", LocalDate.now());

        final Optional<String> s = new ZnHttpUtil().sync.get("http://www.baidu.com", null, data);
        final String s1 = s.orElse("asd");
        System.out.println(s1);
    }
}
