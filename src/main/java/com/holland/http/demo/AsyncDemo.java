package com.holland.http.demo;

import com.holland.http.ZnHttp;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AsyncDemo {

    public static void main(String[] args) throws InterruptedException {
        getDemo();
        postFormDemo();
        postJsonDemo();

        Thread.sleep(1000);
        System.exit(1);
    }

    public static void getDemo() {
        final Map<String, Object> data = new HashMap<>();
        data.put("t1", LocalDateTime.now());
        data.put("t2", LocalTime.now());
        data.put("t3", LocalDate.now());

        new ZnHttp().async.get("http://www.baidu.com", null, data, response -> {
            try {
                System.out.println("getDemo::\n" + response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void postFormDemo() {
        final Map<String, Object> data = new HashMap<>();
        data.put("t1", LocalDateTime.now());
        data.put("t2", LocalTime.now());
        data.put("t3", LocalDate.now());

        new ZnHttp().async.get("http://www.baidu.com", null, data, response -> {
            try {
                System.out.println("postFormDemo::\n" + response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void postJsonDemo() {
        final Map<String, Object> data = new HashMap<>();
        data.put("t1", LocalDateTime.now());
        data.put("t2", LocalTime.now());
        data.put("t3", LocalDate.now());

        new ZnHttp().async.get("http://www.baidu.com", null, data, response -> {
            try {
                System.out.println("postJsonDemo::\n" + response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
