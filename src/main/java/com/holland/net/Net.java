package com.holland.net;

import com.holland.net.conf.DefaultHttpConf;
import com.holland.net.conf.HttpConf;
import com.holland.net.core.Async;
import com.holland.net.core.Sync;

/**
 * This is main class
 * <p>
 * HttpConf: Uou can use default configuration or custom your config
 * </p>
 * <p>
 * Secondary encapsulation 'OKHTTP'
 * </p>
 */
public class Net {
    private final HttpConf httpConf;
    public final Sync sync;
    public final Async async;

    public Net() {
        this.httpConf = new DefaultHttpConf();
        this.sync = new Sync(httpConf);
        this.async = new Async(httpConf);
    }

    public Net(HttpConf httpConf) {
        this.httpConf = httpConf;
        this.sync = new Sync(httpConf);
        this.async = new Async(httpConf);
    }

}
