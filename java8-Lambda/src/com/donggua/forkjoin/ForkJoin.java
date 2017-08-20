package com.donggua.forkjoin;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * java8 并行 串行
 *
 * Created by donggua on 2017/8/20.
 */
public class ForkJoin {

    private static final long END_VALUE = 10000000000L;

    // fork join
    @Test
    public void test1(){
        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0, END_VALUE);

        Long sum = pool.invoke(task);
        System.out.println(sum);

        Instant end = Instant.now();
        System.out.println("耗时：" + Duration.between(start, end).toMillis());
    }

    // 普通for循环
    @Test
    public void test2(){
        Instant start = Instant.now();

        long sum = 0L;

        for (long i = 0; i <= END_VALUE; i ++){
            sum += i;
        }

        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("耗时：" + Duration.between(start, end).toMillis());
    }

    //java8 的并行流测试
    @Test
    public  void test3(){
        Instant start = Instant.now();

        LongStream.rangeClosed(0, END_VALUE)
                .parallel()
                .reduce(0, Long::sum);

        Instant end = Instant.now();

        System.out.println("耗时为：" + Duration.between(start, end).toMillis());
    }

    // 可用处理器
    @Test
    public  void test4(){
        int num = Runtime.getRuntime().availableProcessors();
        System.out.println(num);
    }
}
