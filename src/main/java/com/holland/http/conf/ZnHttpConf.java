package com.holland.http.conf;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Map;

/**
 * This is a configuration class
 */
public interface ZnHttpConf {

    /**
     * Inject your OkHttpClient
     */
    OkHttpClient getClient();

    /**
     * Set the global request config
     */
    Request.Builder myRequest(Map<String, String> headers);

    /**
     * Print the error message, when the request failed
     * <p>
     * Support for importing Logger, Default use 'System.err'
     * </p>
     *
     * @param s    errMsg
     * @param args errParams
     */
    void printError(String s, Object... args);

    /**
     * Different data types can be configured with different data conversion formats
     */
    String formatParam(Object param);

    /**
     * Different data types can be configured with different data conversion formats
     * <p>
     * Support for different JSON util
     * </p>
     */
    String toJson(Object data);
}
