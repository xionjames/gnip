package dev.xionjames.gnip.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores a HashMap to keep a kind of cache
 * @author xionjames
 */
public class CacheManager {
    private static CacheManager manager;
    private Map<String, Object> cache;

    public static CacheManager getInstance() {
        if (manager == null) {
            manager = new CacheManager();
        }

        return manager;
    }

    public CacheManager() {
        this.cache = new HashMap<String, Object>();
    }

    public synchronized void put(String key, Object value) {
        this.cache.put(key, value);
    }

    public Object get(String key) {
        return this.cache.get(key);
    }

    public boolean hasKey(String key) {
        return this.cache.containsKey(key);
    }
}