package com.zc.study.guardedSuspension;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @author zhangchong
 * @CodeReviewer zhangqingan
 * @Description 保护性地暂停
 */
public class GuardedObject<T> {
    /**
     * 受保护的对象
     */
    T obj;
    final Lock lock = new ReentrantLock();
    final Condition done = lock.newCondition();
    final int timeout = 2;

    /**
     * 保存所有GuardedObject
     */
    final static Map<Object, GuardedObject> gos = new ConcurrentHashMap<>();

    static <K, T> void fireEvent(K key, T obj) {
        GuardedObject go = gos.remove(key);
        if (go != null) {
            go.onChanged(obj);
        }
    }

    static <K> GuardedObject<Message> create(K id) {
        GuardedObject<Message> go = new GuardedObject<>();
        gos.put(id, go);
        return go;
    }

    /**
     * 获取受保护对象
     *
     * @param p
     * @return
     */
    T get(Predicate<T> p) {
        lock.lock();
        try {
            //MESA管程推荐写法
            while (!p.test(obj)) {
                done.await(timeout, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        //返回非空的受保护对象
        return obj;
    }

    /**
     * 事件通知方法
     *
     * @param obj
     */
    void onChanged(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
