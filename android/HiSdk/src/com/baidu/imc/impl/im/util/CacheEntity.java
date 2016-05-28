package com.baidu.imc.impl.im.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheEntity<K, V> {

    private static final float hashTableLoadFactor = 0.75f;

    private LinkedHashMap<K, V> map;
    private int cacheSize;

    /**
     * 缓存实体构造器
     * 
     * @param cacheSize :最大缓存数
     */
    protected CacheEntity(int cacheSize) {
        this.cacheSize = cacheSize;
        int hashTableCapacity = (int) Math.ceil(cacheSize / hashTableLoadFactor) + 1;
        map = new LinkedHashMap<K, V>(hashTableCapacity, hashTableLoadFactor, true) {
            private static final long serialVersionUID = 1;

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > CacheEntity.this.cacheSize;
            }
        };
    }

    protected synchronized V get(K key) {
        return map.get(key);
    }

    protected synchronized void put(K key, V value) {
        // map.remove(key);
        map.put(key, value);
    }

    protected synchronized boolean containsKey(K key) {
        return map.containsKey(key);
    }

    protected synchronized void clear() {
        map.clear();
    }

    protected synchronized int size() {
        return map.size();
    }

    protected synchronized V remove(K key) {
        return map.remove(key);
    }
}
