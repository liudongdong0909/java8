package com.study.thread.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/26
 */
public class ThreadPoolExecutorTest3 {

    public static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolExecutorTest3.class);

    public static void main(String[] args) {

        int corePoolSize = 1;
        int maxPoolSize = 10;
        int blockingQueueCapacity = 10;
        int taskSize = 20;
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(blockingQueueCapacity),
                r -> {
                    Thread thread = new Thread(r);
                    // thread.setDaemon(true);
                    return thread;
                }, new ThreadPoolExecutor.AbortPolicy());

        // 加入此处并行执行很耗时的任务。
        IntStream.range(0, taskSize)
                .boxed()
                .forEach(i -> {
                    LOGGER.info("-------");
                    executorService.execute(() -> {
                        while (true){

                        }
                    });
                });

        // 后面要串行执行其他的任务
       // executorService.shutdown();
       //  try {
       //      executorService.awaitTermination(5, TimeUnit.SECONDS);
       //  } catch (InterruptedException e) {
       //      e.printStackTrace();
       //  }

        executorService.shutdownNow();
        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("===============开始执行串行任务=====================");

    }
}
