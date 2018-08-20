package com.study.thread.lock;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/19
 */
public class DeadLock {

    private OtherService otherService;

    public DeadLock(OtherService otherService) {
        this.otherService = otherService;
    }

    private final Object lock = new Object();

    public void m1() {
        synchronized (lock) {
            System.out.println("m1");
            otherService.s1();
        }
    }

    public void m2() {
        synchronized (lock) {
            System.out.println("m2");
        }
    }
}
