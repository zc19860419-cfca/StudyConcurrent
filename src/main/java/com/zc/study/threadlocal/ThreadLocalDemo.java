package com.zc.study.threadlocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangchong
 * @CodeReviewer zhangqingan
 * @Description
 */
public class ThreadLocalDemo {
    private static final int MAX_THREAD_NUM = 10;

    public static void main(String[] args) {
        multiTest();
//        singleTest();
    }

    private static void singleTest() {
        for (int i = 0; i < 10; i++) {
            System.out.println(ThreadId.get());
        }
    }

    private static void multiTest() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 12, 8000L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

        List<Future<Long>> all = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            all.add(executor.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    try{
                        return ThreadId.get();
                    }finally {
                        ThreadId.remove();
                    }

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

        if (!executor.isShutdown()) {
            executor.shutdownNow();
        }
    }
}
