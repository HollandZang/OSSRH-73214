package com.holland.http.demo;

import com.holland.http.ZnHttpUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AsyncGet {

    public static void main(String[] args) {
        final Map<String, Object> data = new HashMap<>();
        data.put("t1", LocalDateTime.now());
        data.put("t2", LocalTime.now());
        data.put("t3", LocalDate.now());

        new ZnHttpUtil().async.get("http://www.baidu.com", null, data, response -> {
            try {
                System.out.println(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.exit(1);
            }
        });
    }
}
