package com.holland.net.conf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DefaultHttpConf implements HttpConf {

    @Override
    public OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public Request.Builder getRequest(Map<String, ?> headers) {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*");
        if (headers != null) {
            headers.forEach((name, value) -> builder.addHeader(name, value == null ? "" : value.toString()));
        }
        return builder;
    }

    @Override
    public void printError(String s, Object... args) {
        final String format = String.format(s.replace("{}", "%s"), args);
        System.err.println(format);
        final Object exception = args[args.length - 1];
        if (exception instanceof Exception)
            System.err.println(Arrays.stream(((Exception) exception).getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n\t")));
    }

    @Override
    public String formatParam(Object param) {
        if (param instanceof Date) {
            final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter.format((Date) param);
        } else if (param instanceof LocalDateTime) {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format((Temporal) param);
        } else if (param instanceof LocalDate) {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd").format((Temporal) param);
        } else if (param instanceof LocalTime) {
            return DateTimeFormatter.ofPattern("HH:mm:ss").format((Temporal) param);
        } else {
            return param.toString();
        }
    }

    @Override
    public String toJson(Object data) {
        final Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return data != null ? gson.toJson(data) : "";
    }
}
