package com.study.thread.lock;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/19
 */
public class DeadLockTest {
    public static void main(String[] args) {

        /**
         * jstack pid
         */

        OtherService otherService = new OtherService();

        DeadLock deadLock = new DeadLock(otherService);

        otherService.setDeadLock(deadLock);

        new Thread(() -> {
            while (true) {
                deadLock.m1();
            }
        }).start();

        new Thread(() -> {
            while (true)
                otherService.s2();
        }).start();
    }
}
