package com.holland.net.example;

import com.holland.net.Net;
import com.holland.net.common.PairBuilder;
import com.holland.net.exception.NetExceptionEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class SyncDemo {

    public static void main(String[] args) {
        getDemo();
        postFormDemo();
        postJsonDemo();
    }

    public static void getDemo() {
        final PairBuilder data = new PairBuilder()
                .add("t1", LocalDateTime.now())
                .add("t2", LocalTime.now())
                .add("t3", LocalDate.now());

        final Optional<String> s = new Net().sync.get("http://www.baidu.com", data, data);
        if (!s.isPresent())
             throw NetExceptionEnum.EMPTY_BODY.e();
        final String s1 = s.get();
        System.out.println("getDemo::\n" + s1);
    }

    public static void postFormDemo() {
        final PairBuilder data = new PairBuilder()
                .add("t1", LocalDateTime.now())
                .add("t2", LocalTime.now())
                .add("t3", LocalDate.now());

        final Optional<String> s = new Net().sync.postForm("http://www.baidu.com", data, data);
        if (!s.isPresent())
            throw NetExceptionEnum.EMPTY_BODY.e();
        final String s1 = s.get();
        System.out.println("postFormDemo::\n" + s1);
    }

    public static void postJsonDemo() {
        final PairBuilder data = new PairBuilder()
                .add("t1", LocalDateTime.now())
                .add("t2", LocalTime.now())
                .add("t3", LocalDate.now());

        final Optional<String> s = new Net().sync.postJson("http://www.baidu.com", data, data);
        if (!s.isPresent())
            throw NetExceptionEnum.EMPTY_BODY.e();
        final String s1 = s.get();
        System.out.println("postJsonDemo::\n" + s1);
    }
}
