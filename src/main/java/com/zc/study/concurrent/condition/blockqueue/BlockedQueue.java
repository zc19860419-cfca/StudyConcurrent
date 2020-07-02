package com.zc.study.concurrent.condition.blockqueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangchong
 * @CodeReviewer zhangqingan
 * @Description
 */

public class BlockedQueue<T> {
    final Lock lock = new ReentrantLock();
    /**
     * 条件变量：队列不满
     */
    final Condition notFull =
            lock.newCondition();
    /**
     * 条件变量：队列不空
     */
    final Condition notEmpty =
            lock.newCondition();

    /**
     * 入队
     *
     * @param x
     */
    void enq(T x) {
        lock.lock();
        try {
            try{
                while (isFull()) {
                    // 等待队列不满
                    notFull.await();
                }
            }catch (Exception e){

            }

            // 省略入队操作...
            //入队后,通知可出队
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    private boolean isFull() {
        return false;
    }

    /**
     * 出队
     */
    void deq() {
        lock.lock();
        try {
            try {
                while (isEmpty()) {
                    // 等待队列不空
                    notEmpty.await();
                }
            }catch (Exception e){

            }
            // 省略出队操作...
            //出队后，通知可入队
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }

    private boolean isEmpty() {
        return false;
    }
}
