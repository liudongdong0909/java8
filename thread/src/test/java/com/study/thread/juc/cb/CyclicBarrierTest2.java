package com.study.thread.juc.cb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * $$
 *
 * 1. CountDownLatch 不能reset，CyclicBarrier 可以循环使用
 * 2. CountDownLatch工作线程之间互不关心, CyclicBarrier 工作线程必须等到同一个共同的点才去执行任务。
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/25
 */
public class CyclicBarrierTest2 {

    public static final Logger LOGGER = LoggerFactory.getLogger(CyclicBarrierTest2.class);

    public static void main(String[] args) throws InterruptedException {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () ->{
           LOGGER.info("all thread finished ");
        });

        new Thread("T1"){
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(6);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread("T2"){
            @Override
            public void run() {
                try {
                    // TimeUnit.SECONDS.sleep(1);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        // TimeUnit.SECONDS.sleep(4);
        // LOGGER.info("1. waiting number -> " + cyclicBarrier.getNumberWaiting());
        // LOGGER.info("1. parties -> " + cyclicBarrier.getParties());
        // LOGGER.info("1. broken  -> " + cyclicBarrier.isBroken());

        TimeUnit.MICROSECONDS.sleep(2);

        cyclicBarrier.reset();

        // LOGGER.info("2. waiting number -> " + cyclicBarrier.getNumberWaiting());
        // LOGGER.info("2. parties -> " + cyclicBarrier.getParties());
        // LOGGER.info("2. broken  -> " + cyclicBarrier.isBroken());

    }


}
