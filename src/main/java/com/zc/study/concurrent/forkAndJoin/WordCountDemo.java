package com.zc.study.concurrent.forkAndJoin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @Author: zhangchong
 * @Description:
 */
public class WordCountDemo {
    public Map<String, Long> countWords(String[] words) {
        //创建ForkAndJoin线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        //创建并行任务
        MR mr = new MR(words, 0, words.length);
        //启动并行任务
        return forkJoinPool.invoke(mr);
    }

    //MR模拟类
    static class MR extends RecursiveTask<Map<String, Long>> {

        private final String[] words;
        private final int start;
        private final int end;

        public MR(String[] words, int from, int to) {
//            System.out.println("(" + from + "," + to + ")");
            this.words = words;
            this.start = from;
            this.end = to;
        }

        @Override
        protected Map<String, Long> compute() {
            Map<String, Long> result;
            if (end - start == 1) {
                result = calc(words[start]);
            } else {
                int mid = start + ((end - start) >> 1);
                MR mr1 = new MR(words, start, mid);
                //启动子任务
                mr1.fork();
                MR mr2 = new MR(words, mid, end);
                //计算子任务，并返回合并结果
                result = merge(mr2.compute(), mr1.join());
            }
            return result;
        }

        /**
         * 合并结果集合
         *
         * @param result1
         * @param result2
         * @return
         */
        private Map<String, Long> merge(Map<String, Long> result1, Map<String, Long> result2) {
            Map<String, Long> result = new HashMap<>();
            result.putAll(result1);
            //合并结果
            result2.forEach((k, v) -> {
                Long count = result.get(k);
                if (count != null) {
                    result.put(k, v + count);
                } else {
                    result.put(k, v);
                }
            });
            return result;
        }

        /**
         * 统计单词数量
         *
         * @param line
         * @return
         */
        private Map<String, Long> calc(String line) {
            Map<String, Long> result = new HashMap<>();
            //1或者更长长度的空白字符 作为分隔符
            String[] words = line.split("\\s+");
            //统计单次数量
            Long count;
            for (String word : words) {
                count = result.get(word);
                if (count == null) {
                    result.put(word, 1L);
                } else {
                    result.put(word, count + 1L);
                }
            }
            return result;
        }
    }
}
