package com.study.thread.synch;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/19
 */
public class SynchronizedThis {


    public static void main(String[] args) {

        ThisLock thisLock = new ThisLock();

        new Thread("T1") {
            @Override
            public void run() {
                thisLock.m1();
            }
        }.start();

        new Thread("T2") {
            @Override
            public void run() {
                thisLock.m2();
            }
        }.start();
    }

}

class ThisLock {

    public synchronized void m1() {
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void m2() {
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
