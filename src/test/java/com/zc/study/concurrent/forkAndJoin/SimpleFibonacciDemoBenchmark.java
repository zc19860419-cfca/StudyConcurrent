package com.zc.study.concurrent.forkAndJoin;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zhangchong
 * @Description:
 */
@BenchmarkMode({Mode.Throughput, Mode.AverageTime}) // 测试方法平均执行时间和吞吐量
@OutputTimeUnit(TimeUnit.SECONDS) // 输出结果的时间粒度为微秒
@State(Scope.Thread) // 默认的State，每个测试线程分配一个实例
public class SimpleFibonacciDemoBenchmark {

    private SimpleFibonacciDemo simpleFibonacciDemo;

    @Setup
    public void prepare() throws Exception {
        simpleFibonacciDemo = new SimpleFibonacciDemo();
    }

    @Benchmark
    public Integer runDemo() throws Exception {
        return simpleFibonacciDemo.runDemo();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(SimpleFibonacciDemoBenchmark.class.getSimpleName())
                .forks(1)
                .threads(4)
                .warmupIterations(3)
                .measurementIterations(10)
                .resultFormat(ResultFormatType.JSON)
                .result("TestData/SimpleFibonacciDemoBenchmark.json")
                .build();
        new Runner(options).run();
    }
}