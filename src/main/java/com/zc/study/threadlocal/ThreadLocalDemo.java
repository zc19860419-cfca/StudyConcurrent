package com.zc.study.threadlocal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangchong
 * @CodeReviewer zhangqingan
 * @Description 每个线程对应唯一的一个ID
 */
public class ThreadLocalDemo {
    private static final int MAX_TASK_NUM = 200;

    public static void main(String[] args) {
        multiTest();
//        singleTest();
    }

    private static void singleTest() {
        for (int i = 0; i < MAX_TASK_NUM; i++) {
            System.out.println(ThreadId.get());
        }
    }

    private static Set<Long> count = new HashSet<>();

    private static void multiTest() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(200, 200, 8000L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

        List<Future<Long>> all = new ArrayList<>();
        for (int i = 0; i < MAX_TASK_NUM; i++) {
            all.add(executor.submit(() -> {
                try {
                    final long threadId = ThreadId.get();
                    count.add(threadId);
                    return threadId;
                } finally {
                    ThreadId.remove();
                }

            }));
        }

        List<Long> result = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            try {
                result.add(all.get(i).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        result.stream().forEach(item -> {
            System.out.println(item);
        });

        System.out.println("size=" + count.size());

        if (!executor.isShutdown()) {
            executor.shutdownNow();
        }
    }
}
