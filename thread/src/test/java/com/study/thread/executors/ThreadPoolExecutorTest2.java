package com.study.thread.executors;

import java.sql.Time;
import java.util.List;
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
public class ThreadPoolExecutorTest2 {

    public static void main(String[] args) {

        int corePoolSize = 1;
        int maxPoolSize = 10;
        int blockingQueueCapacity = 10;
        int taskSize = 20;
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(blockingQueueCapacity),
                r -> {
                    Thread thread = new Thread(r);
                    return thread;
                }, new ThreadPoolExecutor.AbortPolicy());

        IntStream.range(0, taskSize)
                .boxed()
                .forEach(i -> {
                    executorService.execute(() -> {
                        try {
                            // TimeUnit.SECONDS.sleep(10);
                            TimeUnit.SECONDS.sleep(5);
                            System.out.println(Thread.currentThread().getName() + "[ " + i + "]");
                        } catch (InterruptedException e) {
                            // e.printStackTrace();
                        }
                    });
                });

        /**
         * 1. shutdown()方法分析：
         *
         * 20 threads
         *  |- 10 工作
         *  |- 10 等待
         *
         * 调用 shutdown()
         *  1. 10 个工作中的线程等待完成
         *  2. 10  idle thread interrupt
         *  3. 20 空闲线程退出
         *
         */
        /**
         * 启动一个有序的关闭，之前提交的任务将被执行，但是不会接受新的任务。如果已经关闭，则调用没有其他效果。
         * 此方法不等待之前提交的任务完成之后执行。 - 即showdown(）不会阻塞线程执行， 是非阻塞的
         * 使用awaitTermination来完成这项工作。 - 要想使线程阻塞执行，就使用 awaitTermination(long timeout, TimeUnit unit)
         */
        // executorService.shutdown();
        // try {
        //     executorService.awaitTermination(10, TimeUnit.HOURS);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // System.out.println("=====所有线程执行完毕退出======");


        /**
         * 2. shutdownNow()方法分析
         * 20 threads - 核心线程 10， 队列大小 10
         *    |- 10 工作
         *    |- 10 等待（阻塞队列中）
         *
         * 调用 shutdownNow()方法，
         * 1. 返回List<Runnable>，返回的是queue中的线程
         * 2. interrupt 20 个线程, 不等待工作中的线程完成，就直接退出了。
         *
         */
        /**
         * 尝试停止所有正在执行的任务，停止等待任务的处理，并返回等待执行任务的列表。这些任务在从此方法返回时从任务队列中提取(删除)。
         * 此方法不等待正在执行的任务终止。- 即shutdownNow() 也不会阻塞线程执行，是非阻塞的。
         * 使用awaitTermination来完成这项工作 - 要想使线程阻塞执行，就使用 awaitTermination(long timeout, TimeUnit unit)
         *
         * 除了尽最大努力停止积极执行任务的处理之外，没有其他保证。这个实现通过Thread.interrupt()取消任务，因此任何未能响应中断的任务可能永远不会终止。
         *
         *
         * 返回值： 未开始执行的任务列表
         */

        List<Runnable> runnableList = null;
        try {
            runnableList = executorService.shutdownNow();
            executorService.awaitTermination(1, TimeUnit.HOURS);
            System.out.println("=====所有线程执行完毕退出======");
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(runnableList);
        System.out.println(runnableList.size());

    }
}
