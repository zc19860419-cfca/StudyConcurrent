package com.zc.study.concurrent.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangchong
 * @CodeReviewer zhangqingan
 * @Description 分三个任务
 * 任务1：洗水壶，烧开水
 * 任务2：洗茶壶，洗茶杯和拿茶叶
 * 任务3：泡茶
 */
public class CompletableFutureDemo {
    public void run(){
        //任务1
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(()->{
            System.out.println("T1:洗水壶....");
            sleep(1,TimeUnit.SECONDS);
            System.out.println("T1:烧开水....");
            sleep(15,TimeUnit.SECONDS);
        });

        //任务2
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(()->{
            System.out.println("T2:洗茶壶....");
            sleep(1,TimeUnit.SECONDS);

            System.out.println("T2:洗茶杯....");
            sleep(2,TimeUnit.SECONDS);

            System.out.println("T2:拿茶叶....");
            sleep(1,TimeUnit.SECONDS);
            return "龙井";
        });

        //任务3：任务1和任务2结束后执行泡茶
        CompletableFuture<String> task3 =task1.thenCombine(task2, (__, tf)->{
            System.out.println("T3:拿到茶叶："+tf);
            System.out.println("T3:泡茶...");
            return "上茶："+tf;
        });

        System.out.println(task3.join());
    }

    private void sleep(int time, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
