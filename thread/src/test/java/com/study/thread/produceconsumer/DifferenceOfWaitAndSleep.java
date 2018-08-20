package com.study.thread.produceconsumer;

import java.util.stream.Stream;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/19
 */
public class DifferenceOfWaitAndSleep {

    /**
     * 1. sleep is the method of Thread, but the wait is the method of Object.
     * <p>
     * 2. sleep will not release the object monitor(Lock), but the wait will the release the monitor and add to the Object monitor waiting queue.
     * <p>
     * 3. use sleep not depend on the monitor, but wait need.
     * <p>
     * 4. the sleep method not need be wakeup, but wait need . (wait(10))
     */

    private static final Object LOCK = new Object();


    public static void main(String[] args) {
        ///////////  2   ///////////
        Stream.of("T1", "T2")
                .forEach(name ->
                        new Thread(name) {
                            @Override
                            public void run() {
                                m1();
                            }
                        }.start()
                );

        Stream.of("T1", "T2")
                .forEach(name ->
                        new Thread(name) {
                            @Override
                            public void run() {
                                m2();
                            }
                        }.start()
                );

        ///////////  3   //////////
        // m1();
        // m2();
        // m22();
    }

    //////////////////2.线程使用 sleep() 获取锁之后不释放锁，待执行完毕之后才释放锁， wait() 释放锁并加入到等待线程队列中 ////////////////
    public static void m1() {
        synchronized (LOCK) {
            try {
                System.out.println("the thread " + Thread.currentThread().getName() + " enter .");
                Thread.sleep(5_000);
                System.out.println("线程执行时间：" + 5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void m2() {
        synchronized (LOCK) {
            try {
                System.out.println("the thread " + Thread.currentThread().getName() + " enter .");
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    ////////////////// 3. sleep() 不依赖于锁， 但是wait() 依赖锁，不然抛出 IllegalMonitorStateException //////////////////
   /* public static void m1() {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    *//**
     * Exception in thread "main" java.lang.IllegalMonitorStateException
     * at java.lang.Object.wait(Native Method)
     *
     * 所以要加锁
     *//*
    public static void m2() {
        try {
            LOCK.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void m22(){
        synchronized (LOCK){
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
*/
}
