package com.zc.study.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhangchong
 * @CodeReviewer zhangqingan
 * @Description
 */

class Cache1<K, V> {
    final Map<K, V> m = new HashMap<>();
    final ReadWriteLock rwl = new ReentrantReadWriteLock();
    /**
     * 读锁
     */
    final Lock r = rwl.readLock();
    /**
     * 写锁
     */
    final Lock w = rwl.writeLock();

    /**
     * 读缓存
     *
     * @param key
     * @return
     */
    V get(K key) {
        r.lock();
        try {
            return m.get(key);
        } finally {
            r.unlock();
        }
    }

    /**
     * 写缓存
     *
     * @param key
     * @param value
     * @return
     */
    V put(K key, V value) {
        w.lock();
        try {
            return m.put(key, value);
        } finally {
            w.unlock();
        }
    }
}
