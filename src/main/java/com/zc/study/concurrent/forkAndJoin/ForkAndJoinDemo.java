package com.zc.study.concurrent.forkAndJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @Author: zhangchong
 * @Description: Fork/Join 是一个并行计算的框架，主要就是用来支持分治任务模型的
 * 这个计算框架里的 Fork 对应的是分治任务模型里的任务分解，Join 对应的是结果合并。
 */
public class ForkAndJoinDemo {

    public static void main(String[] args) {
        final ForkAndJoinDemo demo = new ForkAndJoinDemo();
        Integer result = demo.runDemo();
        //输出结果
        System.out.println(result);
    }

    public Integer runDemo() {
        //创建分治任务线程池
        ForkJoinPool fjp = new ForkJoinPool(4);
        //创建分治任务
        Fibonacci fib = new Fibonacci(30);
        //启动分治任务
        return fjp.invoke(fib);

    }

    //递归任务
    static class Fibonacci extends RecursiveTask<Integer> {
        final int n;

        Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            //创建子任务
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            //等待子任务结果，并合并结果
            return f2.compute() + f1.join();
        }
    }
}
