package com.zc.study;

/**
 * @author zhangchong
 * @CodeReviewer zhangqingan
 * @Description
 */
public class Multi1JNI {
    static {
        System.loadLibrary("HelloJNI");
    }

    private native String sayHello();

    public String say() {
        return sayHello();
    }
}
