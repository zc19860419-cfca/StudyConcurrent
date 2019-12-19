package com.zc.study;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {
    private static final int MAX_THREAD_NUM = 3;

    public static void main(String[] args) {
        multiTest();
//        singleTest();
    }

    private static void singleTest() {
        System.out.println(new HelloJNI().say());
    }

    private static void multiTest() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(MAX_THREAD_NUM, MAX_THREAD_NUM + 2, 8000L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        List<Future<String>> all = new ArrayList<>();
        for (int i = 0; i < MAX_THREAD_NUM; i++) {
            all.add(executor.submit(() -> new HelloJNI().say()));
            all.add(executor.submit(() -> new Multi1JNI().say()));
        }

        List<String> result = new ArrayList<>();
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
