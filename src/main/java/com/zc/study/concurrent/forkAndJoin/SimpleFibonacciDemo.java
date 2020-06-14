package com.zc.study.concurrent.forkAndJoin;

/**
 * @Author: zhangchong
 * @Description:
 */
public class SimpleFibonacciDemo {
    public Integer runDemo() {
        return fib(30);

    }

    private int fib(int n) {
        if (n <= 1) {
            return n;
        }
        return fib(n - 1) + fib(n - 2);
    }
}
