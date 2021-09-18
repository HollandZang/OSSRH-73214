package com.holland.http.core;

import com.holland.http.common.CommonUtil;
import com.holland.http.conf.ZnHttpConf;
import okhttp3.*;

import java.util.Map;
import java.util.Optional;

import static com.holland.http.common.CommonUtil.MEDIA_TYPE_JSON;

public class Sync {
    private final ZnHttpConf znHttpConf;

    public Sync(ZnHttpConf conf) {
        this.znHttpConf = conf;
    }

    public Optional<String> get(String url, Map<String, String> headers, Map<String, ?> data) {
        final HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        CommonUtil.setEncodeQueryParam(data, urlBuilder::addEncodedQueryParameter, znHttpConf);

        final Request request = znHttpConf.myRequest(headers)
                .get()
                .url(urlBuilder.build())
                .build();

        return getOptionalString(url, data, request);
    }

    public Optional<String> postForm(String url, Map<String, String> headers, Map<String, ?> data) {
        final FormBody.Builder formBodyBuilder = new FormBody.Builder();
        CommonUtil.setEncodeQueryParam(data, formBodyBuilder::addEncoded, znHttpConf);

        final Request request = znHttpConf.myRequest(headers)
                .post(formBodyBuilder.build())
                .url(url)
                .build();

        return getOptionalString(url, data, request);
    }

    public Optional<String> postJson(String url, Map<String, String> headers, String json) {
        final RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);

        final Request request = znHttpConf.myRequest(headers)
                .post(requestBody)
                .url(url)
                .build();

        return getOptionalString(url, json, request);
    }

    public Optional<String> postJson(String url, Map<String, String> headers, Object data) {
        final String json = znHttpConf.toJson(data);
        return postJson(url, headers, json);
    }

    private Optional<String> getOptionalString(String url, Object data, Request request) {
        try {
            final Response execute = znHttpConf.getClient().newCall(request).execute();
            final String string = execute.body().string();
            return Optional.of(string);
        } catch (Exception e) {
            znHttpConf.printError("request error: {}\nheaders: {}\ndata: {}", url, request.headers(), data, e);
            return Optional.empty();
        }
    }
}
