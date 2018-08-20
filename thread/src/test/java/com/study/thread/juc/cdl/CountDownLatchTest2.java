package com.study.thread.juc.cdl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/20
 */
public class CountDownLatchTest2 {

    public static final Logger LOGGER = LoggerFactory.getLogger(CountDownLatchTest2.class);

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(() -> {
            LOGGER.info(Thread.currentThread().getName() + "开做一些初始化的操作 。。。");
            try {
                Thread.sleep(5000);
                countDownLatch.await();

                LOGGER.info(Thread.currentThread().getName() + "开始去做一些其他的操作 。。。。");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "T1").start();

        new Thread(() -> {
            LOGGER.info(Thread.currentThread().getName() + "异步处理数据。。。");

            try {
                Thread.sleep(2000);

                LOGGER.info(Thread.currentThread().getName() + "异步处理数据完成 。。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }

        }, "T2").start();

        new Thread(() -> {

            try {
                /**
                 *
                 * 00:02:39.827 [T2] INFO com.study.thread.juc.cdl.CountDownLatchTest2 - T2异步处理数据。。。
                 * 00:02:39.827 [T1] INFO com.study.thread.juc.cdl.CountDownLatchTest2 - T1开做一些初始化的操作 。。。
                 * 00:02:41.832 [T2] INFO com.study.thread.juc.cdl.CountDownLatchTest2 - T2异步处理数据完成 。。。
                 * 00:02:41.833 [T3] INFO com.study.thread.juc.cdl.CountDownLatchTest2 - T3 ====== WAIT ======
                 * 00:02:44.830 [T1] INFO com.study.thread.juc.cdl.CountDownLatchTest2 - T1开始去做一些其他的操作 。。。。
                 *
                 */
                countDownLatch.await();
                LOGGER.info(Thread.currentThread().getName() + " ====== WAIT ======  ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T3").start();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
