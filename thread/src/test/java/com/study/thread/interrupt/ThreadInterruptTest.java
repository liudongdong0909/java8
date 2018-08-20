package com.study.thread.interrupt;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/18
 */
public class ThreadInterruptTest {

    /**
     * 除非当前线程中断自身(这总是允许的)，否则调用该线程的checkAccess方法，这可能导致引发SecurityException。
     *
     * 1. 如果这个线程在调用对象类的
     *    wait()、wait(long)、wait(long, int)方法，
     * 或 join()、join(long, long)、join(long, int)方法、
     * 或 sleep(long, int)、sleep(long, int)方法
     * 时被阻塞，那么它的中断状态将被清除，它将接收一个InterruptedException。
     *
     * 2. 如果在InterruptibleChannel上的I/O操作中阻塞了这个线程，那么通道将被关闭，线程的中断状态将被设置，并且线程将接收到一个被interruptexception关闭的消息。
     *
     * 3. 如果这个线程在选择器中被阻塞，那么线程的中断状态将被设置，它将立即从选择操作返回，可能会返回一个非零值，就像调用了选择器的唤醒方法一样。
     *
     * 4. 如果以上条件都不成立，则会设置此线程的中断状态。
     *
     * 5. 中断一个没有生命的线程不需要任何影响。
     * @param args
     */

   /* public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                while (true) {
                    // TODO
                    System.out.println("interrupt " + this.isInterrupted());
                }
            }
        };

        t1.start();
        Thread.sleep(100);
        System.out.println("thread sleep -> interrupt: " + t1.isInterrupted());
        t1.interrupt();
        System.out.println("thread sleep -> interrupe: " + t1.isInterrupted());
    }*/

/*
    public static void main(String[] args) {
        Thread t2 = new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("打断类型： sleep interrupt");
                        e.printStackTrace();
                    }
                }
            }
        };

        t2.start();
        System.out.println("thread sleep -> interrupt: " + t2.isInterrupted());
        t2.interrupt();
        System.out.println("thread sleep -> interrupe: " + t2.isInterrupted());
    }*/

   /* public static final Object MONITOR = new Object();

    public static void main(String[] args) {
        Thread t3 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    synchronized (MONITOR) {
                        try {
                            MONITOR.wait(10);
                        } catch (InterruptedException e) {
                            System.out.println("打断类型：wait interrupt");
                            System.out.println(currentThread().isInterrupted());
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        t3.start();
        System.out.println("thread sleep -> interrupt: " + t3.isInterrupted());
        t3.interrupt();
        System.out.println("thread sleep -> interrupe: " + t3.isInterrupted());
    }*/

    public static void main(String[] args) {
        Thread t4 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    // System.out.println(Thread.currentThread().getName() + "===" + this.isInterrupted());
                }
            }
        };
        t4.start();

        // 解决不能中断的问题
        Thread main = Thread.currentThread();

        Thread t5 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + "> sleep");
                    e.printStackTrace();
                }

                //t4.interrupt();
                main.interrupt();
                System.out.println(Thread.currentThread().getName() + " > interrupt");
            }
        };
        t5.start();

        try {
            // join 的是 main 线程， 但是打断的是 t4线程
            // 等待这个线程死亡 -> 谁在等待这个线程死亡? -> main -> t5里面应该中断 main
            t4.join();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " > 打断类型 : join  interrupt");
            e.printStackTrace();
        }

        System.out.println("t4\t" + t4.isInterrupted());
        System.out.println("t5\t" + t5.isInterrupted());
        System.out.println("main\t" + main.isInterrupted());
    }

}
