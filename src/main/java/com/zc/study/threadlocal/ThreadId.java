package com.zc.study.threadlocal;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * @author zhangchong
 * @CodeReviewer zhangqingan
 * @Description
 */
public class ThreadId {
    private static final AtomicLong nextId = new AtomicLong(0);
    private static final ThreadLocal<Long> threadLocal = ThreadLocal.withInitial(new Supplier<Long>() {
        @Override
        public Long get() {
            return nextId.getAndIncrement();
        }
    });

    public static long get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }
}
