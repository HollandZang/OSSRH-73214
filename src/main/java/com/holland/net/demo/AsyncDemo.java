package com.holland.net.demo;

import com.holland.net.Net;
import com.holland.net.common.PairBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AsyncDemo {

    public static void main(String[] args) throws InterruptedException {
        getDemo();
        postFormDemo();
        postJsonDemo();

        Thread.sleep(1000);
        System.exit(1);
    }

    public static void getDemo() {
        final PairBuilder data = new PairBuilder()
                .add("t1", LocalDateTime.now())
                .add("t2", LocalTime.now())
                .add("t3", LocalDate.now());

        new Net().async.get("http://www.baidu.com", data, data, response -> {
            try {
                System.out.println("getDemo::\n" + response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void postFormDemo() {
        final PairBuilder data = new PairBuilder()
                .add("t1", LocalDateTime.now())
                .add("t2", LocalTime.now())
                .add("t3", LocalDate.now());

        new Net().async.postForm("http://www.baidu.com", data, data, response -> {
            try {
                System.out.println("postFormDemo::\n" + response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void postJsonDemo() {
        final PairBuilder data = new PairBuilder()
                .add("t1", LocalDateTime.now())
                .add("t2", LocalTime.now())
                .add("t3", LocalDate.now());

        new Net().async.postJson("http://www.baidu.com", data, data, response -> {
            try {
                System.out.println("postJsonDemo::\n" + response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
