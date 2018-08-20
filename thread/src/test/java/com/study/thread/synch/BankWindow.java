package com.study.thread.synch;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/18
 */
public class BankWindow {

    public static void main(String[] args) {

        TicketWindowRunnable target = new TicketWindowRunnable();
        Thread t1 = new Thread(target, "窗口1");
        Thread t2 = new Thread(target, "窗口2");
        Thread t3 = new Thread(target, "窗口3");
        Thread t4 = new Thread(target, "窗口4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }
}
