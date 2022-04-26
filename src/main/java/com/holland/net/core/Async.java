package com.holland.net.core;

import com.holland.net.common.CommonUtil;
import com.holland.net.conf.HttpConf;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import static com.holland.net.common.CommonUtil.MEDIA_TYPE_JSON;

public class Async {
    private final HttpConf httpConf;

    public Async(HttpConf conf) {
        this.httpConf = conf;
    }

    public void get(String url, Map<String, ?> headers, Map<String, ?> data, Consumer<Response> onResponse) {
        final HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        CommonUtil.setEncodeQueryParam(data, urlBuilder::addEncodedQueryParameter, httpConf);

        final Request request = httpConf.getRequest(headers)
                .get()
                .url(urlBuilder.build())
                .build();

        doCallback(url, data, request, onResponse);
    }

    public void postForm(String url, Map<String, ?> headers, Map<String, ?> data, Consumer<Response> onResponse) {
        final FormBody.Builder formBodyBuilder = new FormBody.Builder();
        CommonUtil.setEncodeQueryParam(data, formBodyBuilder::addEncoded, httpConf);

        final Request request = httpConf.getRequest(headers)
                .post(formBodyBuilder.build())
                .url(url)
                .build();

        doCallback(url, data, request, onResponse);
    }

    public void postJson(String url, Map<String, ?> headers, String json, Consumer<Response> onResponse) {
        final RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);

        final Request request = httpConf.getRequest(headers)
                .post(requestBody)
                .url(url)
                .build();

        doCallback(url, json, request, onResponse);
    }

    public void postJson(String url, Map<String, ?> headers, Object data, Consumer<Response> onResponse) {
        final String json = httpConf.toJson(data);
        postJson(url, headers, json, onResponse);
    }

    private void doCallback(String url, Object data, Request request, Consumer<Response> onResponse) {
        httpConf.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpConf.printError("request error: {}\nheaders: {}\ndata: {}", url, request.headers(), data, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onResponse.accept(response);
            }
        });
    }
}
