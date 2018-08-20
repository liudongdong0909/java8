package com.study.thread.juc.cdl;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/21
 */
public class CountDownLatchTest3 {

    @Test
    public void test1(){
        CountDownLatch countDownLatch = new CountDownLatch(0);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" ========= COUNT DOWN LATCH 0 ==========");
    }

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread mainThread = Thread.currentThread();

        new Thread("T1"){
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // countDownLatch.countDown();
                mainThread.interrupt();
            }

        }.start();


        // try {
        //     countDownLatch.await(1, TimeUnit.SECONDS);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        System.out.println("======= =========");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
