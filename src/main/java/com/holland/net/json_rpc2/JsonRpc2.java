package com.holland.net.json_rpc2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.holland.net.conf.DefaultHttpConf;
import com.holland.net.conf.HttpConf;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class JsonRpc2 {
    private static final TypeToken<ArrayList<Response<?>>> LIST_RESPONSE_TYPE = new TypeToken<ArrayList<Response<?>>>() {
    };

    public final String url;
    private final HttpConf httpConf;

    public final Sync sync;
    public final Async async;

    public JsonRpc2(String url) {
        this.url = url;
        httpConf = new DefaultHttpConf();
        this.sync = new Sync(httpConf);
        this.async = new Async(httpConf);
    }

    public JsonRpc2(String url, HttpConf httpConf) {
        this.url = url;
        this.httpConf = httpConf;
        this.sync = new Sync(httpConf);
        this.async = new Async(httpConf);
    }

    public class Sync {
        private final com.holland.net.core.Sync net;

        public Sync(HttpConf conf) {
            this.net = new com.holland.net.core.Sync(conf);
        }

        public List<Response<?>> send(Request... requests) {
            final String json = new Gson().toJson(requests);
            final Optional<String> s = net.postJson(url, null, json);

            System.out.println("> send:   " + json + "\n  result: " + s.orElse(null));
            if (!s.isPresent())
                throw new RuntimeException("Response body is null");

            return new Gson().fromJson(s.get(), LIST_RESPONSE_TYPE.getType());
        }

        public <T> T send(String method, Object... params) {
            return send(0, method, params);
        }

        public <T> T send(int id, String method, Object... params) {
            final Request request = new Request(id, method, params);
            final String json = new Gson().toJson(request);

            final Optional<String> s = net.postJson(url, null, json);

            System.out.println("> method: " + method + "\n  param:  " + json + "\n  result: " + s.orElse(null));
            if (!s.isPresent())
                throw new RuntimeException("Response body is null");

            //noinspection unchecked
            final Response<T> r = new Gson().fromJson(s.get(), Response.class);
            if (r.failed())
                throw new RuntimeException(r.error.toString());

            return r.result;
        }
    }

    public class Async {
        private final com.holland.net.core.Async net;

        public Async(HttpConf conf) {
            net = new com.holland.net.core.Async(conf);
        }

        public void send(Consumer<List<Response<?>>> onResponse, Request... requests) {
            final String json = new Gson().toJson(requests);

            net.postJson(url, null, json
                    , response -> {
                        if (response.body() == null)
                            throw new RuntimeException("Response body is null");

                        final String s;
                        try {
                            s = Objects.requireNonNull(response.body()).string();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("> send:   " + json + "\n  result: " + s);

                        final List<Response<?>> responseList = new Gson().fromJson(s, LIST_RESPONSE_TYPE.getType());
                        onResponse.accept(responseList);
                    });
        }

        public <T> void send(String method, Consumer<T> onResponse, Object... params) {
            send(0, method, onResponse, params);
        }

        public <T> void send(int id, String method, Consumer<T> onResponse, Object... params) {
            final Request request = new Request(id, method, params);
            final String json = new Gson().toJson(request);

            net.postJson(url, null, json
                    , response -> {
                        if (response.body() == null)
                            throw new RuntimeException("Response body is null");

                        final String s;
                        try {
                            s = Objects.requireNonNull(response.body()).string();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("> method: " + method + "\n  param:  " + json + "\n  result: " + s);

                        //noinspection unchecked
                        final Response<T> r = new Gson().fromJson(s, Response.class);
                        if (r.failed())
                            throw new RuntimeException(r.error.toString());

                        onResponse.accept(r.result);
                    });
        }
    }
}
