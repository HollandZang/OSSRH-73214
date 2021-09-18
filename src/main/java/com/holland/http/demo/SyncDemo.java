package com.holland.http.demo;

import com.holland.http.ZnHttp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SyncDemo {

    public static void main(String[] args) {
        getDemo();
        postFormDemo();
        postJsonDemo();
    }

    public static void getDemo() {
        final Map<String, Object> data = new HashMap<>();
        data.put("t1", LocalDateTime.now());
        data.put("t2", LocalTime.now());
        data.put("t3", LocalDate.now());

        final Optional<String> s = new ZnHttp().sync.get("http://www.baidu.com", null, data);
        final String s1 = s.orElse("asd");
        System.out.println("getDemo::\n" + s1);
    }

    public static void postFormDemo() {
        final Map<String, Object> data = new HashMap<>();
        data.put("t1", LocalDateTime.now());
        data.put("t2", LocalTime.now());
        data.put("t3", LocalDate.now());

        final Optional<String> s = new ZnHttp().sync.postForm("http://www.baidu.com", null, data);
        final String s1 = s.orElse("asd");
        System.out.println("postFormDemo::\n" + s1);
    }

    public static void postJsonDemo() {
        final Map<String, Object> data = new HashMap<>();
        data.put("t1", LocalDateTime.now());
        data.put("t2", LocalTime.now());
        data.put("t3", LocalDate.now());

        final Optional<String> s = new ZnHttp().sync.postJson("http://www.baidu.com", null, data);
        final String s1 = s.orElse("asd");
        System.out.println("postJsonDemo::\n" + s1);
    }
}
