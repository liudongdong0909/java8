package com.study.thread.synch;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/19
 */
public class SynchronizedClass {

    public static void main(String[] args) {

        new Thread("T1") {
            @Override
            public void run() {
                SynchronizedStatic.m1();
            }
        }.start();

        new Thread("T2") {
            @Override
            public void run() {
                SynchronizedStatic.m2();
            }
        }.start();

        new Thread("T3") {
            @Override
            public void run() {
                SynchronizedStatic.m3();
            }
        }.start();
    }

}

class SynchronizedStatic {

    static {
        try {
            System.out.println("static -> " + Thread.currentThread().getName());
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public synchronized static void m1() {
        try {
            System.out.println("M1 -> " + Thread.currentThread().getName());
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void m2() {
        try {
            System.out.println("M2 -> " + Thread.currentThread().getName());
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void m3() {
        try {
            System.out.println("M3 -> " + Thread.currentThread().getName());
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
