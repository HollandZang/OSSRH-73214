package com.holland.net.common;

import java.util.HashMap;

public class PairBuilder extends HashMap<String, Object> {
    public PairBuilder() {
        super();
    }

    public PairBuilder add(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
