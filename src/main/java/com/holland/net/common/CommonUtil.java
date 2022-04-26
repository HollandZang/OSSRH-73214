package com.holland.net.common;

import com.holland.net.conf.HttpConf;
import okhttp3.MediaType;

import java.util.Map;
import java.util.function.BiConsumer;

public class CommonUtil {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=UTF-8");

    public static void setEncodeQueryParam(Map<String, ?> data, BiConsumer<String, String> setIntoBuilder, HttpConf conf) {
        if (data != null) {
            data.forEach((k, v) -> setIntoBuilder.accept(k, conf.formatParam(v)));
        }
    }
}
