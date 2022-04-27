package com.holland.net.core;

import com.holland.net.common.CommonUtil;
import com.holland.net.conf.HttpConf;
import okhttp3.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.holland.net.common.CommonUtil.MEDIA_TYPE_JSON;

public class Sync {
    private final HttpConf httpConf;

    public Sync(HttpConf conf) {
        this.httpConf = conf;
    }

    public Optional<String> get(String url, Map<String, ?> headers, Map<String, ?> data) {
        final HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        CommonUtil.setEncodeQueryParam(data, urlBuilder::addEncodedQueryParameter, httpConf);

        final Request request = httpConf.getRequest(headers)
                .get()
                .url(urlBuilder.build())
                .build();

        return getOptionalString(url, data, request);
    }

    public Optional<String> postForm(String url, Map<String, ?> headers, Map<String, ?> data) {
        final FormBody.Builder formBodyBuilder = new FormBody.Builder();
        CommonUtil.setEncodeQueryParam(data, formBodyBuilder::addEncoded, httpConf);

        final Request request = httpConf.getRequest(headers)
                .post(formBodyBuilder.build())
                .url(url)
                .build();

        return getOptionalString(url, data, request);
    }

    public Optional<String> postJson(String url, Map<String, ?> headers, String json) {
        final RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);

        final Request request = httpConf.getRequest(headers)
                .post(requestBody)
                .url(url)
                .build();

        return getOptionalString(url, json, request);
    }

    public Optional<String> postJson(String url, Map<String, ?> headers, Object data) {
        final String json = httpConf.toJson(data);
        return postJson(url, headers, json);
    }

    private Optional<String> getOptionalString(String url, Object data, Request request) {
        try {
            final Response execute = httpConf.getClient().newCall(request).execute();
            final String string = Objects.requireNonNull(execute.body()).string();
            return Optional.of(string);
        } catch (Exception e) {
            httpConf.printError("request error: {}\nheaders: {}\ndata: {}", url, request.headers(), data, e);
            return Optional.empty();
        }
    }
}
