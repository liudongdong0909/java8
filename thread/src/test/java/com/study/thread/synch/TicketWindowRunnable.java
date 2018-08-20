package com.study.thread.synch;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/18
 */
public class TicketWindowRunnable implements Runnable {

    private int index = 1;

    private final static int MAX = 1000;

    /*private final Object MONITOR = new Object();

    @Override
    public void run() {

        while (true) {
            synchronized (MONITOR){
                if (index > MAX) {
                    break;
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " 的号码是： " + index++);

            }
        }
    }*/

    /*@Override
    public void run() {
        while (true) {
            if (this.ticket()) {
                break;
            }
        }
    }

    private synchronized boolean ticket() {
        if (index > MAX) {
            return true;
        }

        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "的号码是：" + index++);
        return false;
    }*/

    @Override
    public void run() {
        while (true) {
            if (this.ticket()) {
                break;
            }
        }
    }

    private boolean ticket() {
        synchronized (this) {
            if (index > MAX) {
                return true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "的号码是：" + index++);
            return false;
        }
    }
}
