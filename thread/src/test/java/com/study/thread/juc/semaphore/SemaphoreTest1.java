package com.study.thread.juc.semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * $$
 *
 * 计数信号量。
 * 从概念上讲，信号量维护一组许可。如果有必要，每个get()块直到许可证可用，然后取它。
 * 每个release()都添加一个许可证，可能释放一个阻塞的收购者。但没有实际使用许可证对象;信号量只是保持可用数量的计数，并采取相应的行动。
 *
 * 信号量通常用于限制线程数量，而不能访问某些(物理或逻辑)资源。
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/26
 */
public class SemaphoreTest1 {

    public static final Logger LOGGER = LoggerFactory.getLogger(SemaphoreTest1.class);

    public static void main(String[] args) {

        SemaphoreLock semaphoreLock = new SemaphoreLock();
        for (int i = 0; i < 2; i++) {

            new Thread(() -> {

                try {
                    LOGGER.info(Thread.currentThread().getName() + " is running . ");

                    semaphoreLock.lock();

                    LOGGER.info(Thread.currentThread().getName() + " get # semaphore lock .");

                    TimeUnit.SECONDS.sleep(10);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                } finally {
                    semaphoreLock.unlock();
                    LOGGER.info(Thread.currentThread().getName() + " released . ");
                }

            }, "T" + i).start();
        }

    }

    public static class SemaphoreLock {

        Semaphore semaphore = new Semaphore(1);

        public void lock() {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void unlock() {
            semaphore.release();
        }
    }
}
