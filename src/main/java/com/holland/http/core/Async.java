package com.holland.http.core;

import com.holland.http.common.CommonUtil;
import com.holland.http.conf.ZnHttpConf;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import static com.holland.http.common.CommonUtil.MEDIA_TYPE_JSON;

public class Async {
    private final ZnHttpConf znHttpConf;

    public Async(ZnHttpConf conf) {
        this.znHttpConf = conf;
    }

    public void get(String url, Map<String, String> headers, Map<String, ?> data, Consumer<Response> onResponse) {
        final HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        CommonUtil.setEncodeQueryParam(data, urlBuilder::addEncodedQueryParameter, znHttpConf);

        final Request request = znHttpConf.myRequest(headers)
                .get()
                .url(urlBuilder.build())
                .build();

        doCallback(url, data, request, onResponse);
    }

    public void postForm(String url, Map<String, String> headers, Map<String, ?> data, Consumer<Response> onResponse) {
        final FormBody.Builder formBodyBuilder = new FormBody.Builder();
        CommonUtil.setEncodeQueryParam(data, formBodyBuilder::addEncoded, znHttpConf);

        final Request request = znHttpConf.myRequest(headers)
                .post(formBodyBuilder.build())
                .url(url)
                .build();

        doCallback(url, data, request, onResponse);
    }

    public void postJson(String url, Map<String, String> headers, String json, Consumer<Response> onResponse) {
        final RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);

        final Request request = znHttpConf.myRequest(headers)
                .post(requestBody)
                .url(url)
                .build();

        doCallback(url, json, request, onResponse);
    }

    public void postJson(String url, Map<String, String> headers, Object data, Consumer<Response> onResponse) {
        final String json = znHttpConf.toJson(data);
        postJson(url, headers, json, onResponse);
    }

    private void doCallback(String url, Object data, Request request, Consumer<Response> onResponse) {
        znHttpConf.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                znHttpConf.printError("request error: {}\nheaders: {}\ndata: {}", url, request.headers(), data, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onResponse.accept(response);
            }
        });
    }
}
