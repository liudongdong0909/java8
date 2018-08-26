package com.study.thread.juc.semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * $$
 * <p>
 * 计数信号量。
 * 从概念上讲，信号量维护一组许可。如果有必要，每个get()块直到许可证可用，然后取它。
 * 每个release()都添加一个许可证，可能释放一个阻塞的收购者。但没有实际使用许可证对象;信号量只是保持可用数量的计数，并采取相应的行动。
 * <p>
 * 信号量通常用于限制线程数量，而不能访问某些(物理或逻辑)资源。
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/26
 */
public class SemaphoreTest2 {

    public static final Logger LOGGER = LoggerFactory.getLogger(SemaphoreTest2.class);

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(2);

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                LOGGER.info(Thread.currentThread().getName() + "running");
                try {
                    /**
                     * 从这个信号量获得许可，阻塞直到一个信号量可用，或者线程被中断。
                     *
                     * 获得许可证，如果有许可证，立即返回，可获得许可证的数量减少1。
                     *
                     * 如果没有许可证可用，那么当前线程将为线程调度目的而被禁用并处于休眠状态，直到发生以下两种情况之一:
                     *  1. 其他一些线程调用这个信号量的release()方法，当前线程将被分配一个许可证;
                     *  2. 其他一些线程中断当前线程。
                     *
                     * 如果当前线程:
                     *  1. 在进入此方法时设置其中断状态;
                     *  2. 在等待许可时被打断
                     *
                     * 然后抛出InterruptedException，清除当前线程的中断状态。
                     */
                    semaphore.acquire(2);
                    LOGGER.info(Thread.currentThread().getName() + "get #semaphore lock .");
                    TimeUnit.SECONDS.sleep(6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    /**
                     * 释放许可证，将其返回到信号量。
                     *
                     * 发布许可证，增加一个可用许可证的数量。如果有任何线程试图获得许可证，那么将选择一个线程并给予刚刚发布的许可证。该线程为线程调度目的而启用。
                     *
                     * 没有要求释放许可证的线程必须通过调用acquire()获得该许可证。在应用程序中，通过编程约定确定信号量的正确用法。
                     *
                     */
                    semaphore.release(2);
                    LOGGER.info(Thread.currentThread().getName() + "released . ");
                }

            }, "T" + i).start();
        }

        // 监控
        while (true){
            /**
             * 返回此信号量中可用的许可证的当前数量。
             *
             * 这种方法通常用于调试和测试目的。
             */
            LOGGER.info("AP -> " + semaphore.availablePermits());
            /**
             * 返回等待获取的线程数量的估计值。这个值只是一个估计值，因为当这个方法遍历内部数据结构时，线程的数量可能会动态变化。
             * 该方法用于系统状态的监控，而不是同步控制。
             */
            LOGGER.info("QL -> " + semaphore.getQueueLength());
            System.out.println("=========================================");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
