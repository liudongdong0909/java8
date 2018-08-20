package com.study.thread.produceconsumer.v3;

import java.util.stream.Stream;

/**
 * $$ 单生产者和单消费者
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/19
 */
public class ProduceConsumerV3 {

    private int i = 0;

    private final Object LOCK = new Object();

    private volatile boolean isProduce = false;

    public void produce() {
        synchronized (LOCK) {
            while (isProduce) {
                try {
                    /**
                     *
                     * 导致当前线程等待，直到另一个线程调用此对象的notify()方法或notifyAll()方法。
                     * 换句话说，这个方法的行为就像它简单地执行调用wait(0)一样。
                     *
                     *
                     * 当前线程必须拥有这个对象的监视器。线程释放这个监视器的所有权并等待，
                     * 直到另一个线程通过调用notify方法或notifyAll方法通知等待这个对象监视器的线程。
                     * 然后线程等待，直到它可以重新获得监视器的所有权并恢复执行。
                     *
                     *
                     */
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
            System.out.println(Thread.currentThread().getName() + " -> " + i);
            LOCK.notifyAll();
            isProduce = true;

        }

    }

    public void consumer() {
        synchronized (LOCK) {
            /**
             * 使用if判断 引起
             *
             * PRODUCE1 -> 475
             * CONSUMER4 -> 475
             * CONSUMER1 -> 475
             * CONSUMER3 -> 475
             */
            while (!isProduce) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + " -> " + i);
            /**
             * 唤醒所有等待此对象监视器的线程。线程通过调用一个等待方法来等待对象的监视器。
             *
             * 在当前线程放弃此对象上的锁之前，唤醒的线程将无法继续执行。
             * 唤醒的线程将以通常的方式与可能正在此对象上积极竞争同步的任何其他线程竞争;
             * 例如，唤醒的线程在成为下一个锁定该对象的线程时没有任何可靠的特权或缺点。
             *
             * 此方法只应由该对象监视器的所有者线程调用。有关线程成为监视器所有者的方式的描述，请参阅notify方法。
             *
             */
            LOCK.notifyAll();
            isProduce = false;
        }
    }

    public static void main(String[] args) {

        /**
         *  多生产者 多消费者问题
         */
        ProduceConsumerV3 produceConsumerV2 = new ProduceConsumerV3();

        Stream.of("PRODUCE1", "PRODUCE2").forEach(produceName -> {
            new Thread(produceName) {
                @Override
                public void run() {
                    while (true) {
                        produceConsumerV2.produce();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        });

        Stream.of("CONSUMER1", "CONSUMER2", "CONSUMER3", "CONSUMER4").forEach(consumerName -> {
            new Thread(consumerName) {
                @Override
                public void run() {
                    while (true) {
                        produceConsumerV2.consumer();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        });
    }

}
