package com.zc.study.concurrent.allocator;

import java.util.List;

/**
 * @author zhangchong
 * @CodeReviewer zhangqingan
 * @Description
 */
public class Allocator {
    private List<Object> als;

    synchronized void apply(Object from, Object to) {
        while (als.contains(from) || als.contains(to)) {
            try {
                wait();
            } catch (Exception e) {

            }
        }

        als.add(from);
        als.add(to);
    }

    synchronized void free(Object from,Object to){
        als.remove(from);
        als.remove(to);
        notifyAll();
    }
}
