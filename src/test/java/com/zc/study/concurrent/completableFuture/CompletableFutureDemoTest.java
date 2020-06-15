package com.zc.study.concurrent.completableFuture;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zhangchong
 * @CodeReviewer zhangqingan
 * @Description
 */
public class CompletableFutureDemoTest {

    private CompletableFutureDemo completableFutureDemo;

    @Before
    public void setUp() throws Exception {
        completableFutureDemo = new CompletableFutureDemo();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void run() {
        completableFutureDemo.run();
    }
}