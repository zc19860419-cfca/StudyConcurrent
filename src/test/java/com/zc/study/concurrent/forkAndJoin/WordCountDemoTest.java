package com.zc.study.concurrent.forkAndJoin;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Author: zhangchong
 * @Description:
 */
public class WordCountDemoTest {

    private WordCountDemo wordCountDemo;

    @Before
    public void setUp() throws Exception {
        wordCountDemo = new WordCountDemo();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void countWords() {
        String[] fc = {"hello world",
                "hello me",
                "hello fork",
                "hello join",
                "fork join in world"};
        final Map<String, Long> result = wordCountDemo.countWords(fc);
//        result.forEach((k, v) -> System.out.println(k + ":" + v));
        Assert.assertEquals(6L, result.keySet().size());
        Assert.assertEquals(1L, result.get("me").longValue());
        Assert.assertEquals(2L, result.get("world").longValue());
        Assert.assertEquals(4L, result.get("hello").longValue());
        Assert.assertEquals(2L, result.get("fork").longValue());
        Assert.assertEquals(1L, result.get("in").longValue());
    }
}