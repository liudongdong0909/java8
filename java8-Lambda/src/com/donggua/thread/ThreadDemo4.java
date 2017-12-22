package com.donggua.thread;

/**
 * 生产者消费者1
 *
 * @author IT_donggua
 * @version V1.0
 * @create 2017-12-22 下午 05:04
 */
public class ThreadDemo4 {
    private static boolean flags = false;

    public static void main(String[] args) {
        class Goods {
            private String name;
            private int num;

            public synchronized void produce(String name) {
                if (flags) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.name = name + "编号：" + num++;
                System.out.println("生产了...." + this.name);
                flags = true;
                notifyAll();
            }

            public synchronized void consume() {
                if (!flags) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("消费了******" + name);
                flags = false;
                notifyAll();
            }

        }

        final Goods g = new Goods();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    g.produce("商品");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    g.consume();
                }
            }
        }).start();
    }
}
