package com.holland.http;

import com.holland.http.conf.DefaultZnHttpConf;
import com.holland.http.conf.ZnHttpConf;
import com.holland.http.core.Async;
import com.holland.http.core.Sync;

/**
 * This is main class
 * <p>
 * ZnHttpConf: Uou can use default configuration or custom your config
 * </p>
 * <p>
 * Secondary encapsulation 'OKHTTP'
 * </p>
 */
public class ZnHttp {
    private final ZnHttpConf znHttpConf;
    public final Sync sync;
    public final Async async;

    public ZnHttp() {
        this.znHttpConf = new DefaultZnHttpConf();
        this.sync = new Sync(znHttpConf);
        this.async = new Async(znHttpConf);
    }

    public ZnHttp(ZnHttpConf znHttpConf) {
        this.znHttpConf = znHttpConf;
        this.sync = new Sync(znHttpConf);
        this.async = new Async(znHttpConf);
    }

}
