package com.study.thread.produceconsumer.v2;

import java.util.stream.Stream;

/**
 * $$ 单生产者和单消费者
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/19
 */
public class ProduceConsumerV2 {

    private int i = 0;

    private final Object LOCK = new Object();

    private volatile boolean isProduce = false;

    public void produce() {
        synchronized (LOCK) {
            if (isProduce) {
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
            } else {
                i++;
                System.out.println(Thread.currentThread().getName() + " -> " + i);
                LOCK.notify();
                isProduce = true;
            }
        }

    }

    public void consumer() {
        synchronized (LOCK) {
            if (isProduce) {
                System.out.println(Thread.currentThread().getName() + " -> " + i);
                /**
                 * 唤醒正在等待此对象监视器的单个线程。如果有线程在等待这个对象，那么就选择唤醒其中一个线程。
                 * 这个选择是任意的，由实现决定。线程通过调用一个等待方法来等待对象的监视器。
                 *
                 *
                 * 在当前线程放弃此对象上的锁之前，唤醒的线程将无法继续执行。
                 * 唤醒的线程将以通常的方式与可能正在此对象上积极竞争同步的任何其他线程竞争;
                 * 例如，唤醒的线程在成为下一个锁定该对象的线程时不享有可靠的特权或劣势。
                 *
                 *
                 * 此方法只应由该对象监视器的所有者线程调用。线程通过以下三种方式之一成为对象监视器的所有者:
                 * 1. 通过执行该对象的同步实例方法
                 * 2. 通过执行同步对象的同步语句的主体。
                 * 3. 对于类类型的对象，通过执行该类的同步静态方法。
                 *
                 *
                 * 一次只有一个线程可以拥有一个对象的监视器。
                 */
                LOCK.notify();
                isProduce = false;
            } else {
                try {

                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {

        /**
         *  多生产者 多消费者问题
         */
        ProduceConsumerV2 produceConsumerV2 = new ProduceConsumerV2();

        Stream.of("PRODUCE1", "PRODUCE2").forEach(produceName -> {
            new Thread(produceName) {
                @Override
                public void run() {
                    while (true) {
                        produceConsumerV2.produce();
                    }
                }
            }.start();
        });

        Stream.of("CONSUMER1", "CONSUMER2").forEach(consumerName -> {
            new Thread(consumerName) {
                @Override
                public void run() {
                    while (true) {
                        produceConsumerV2.consumer();
                    }
                }
            }.start();
        });
    }

    /*public static void main(String[] args) {

        ProduceConsumerV2 produceConsumerV2 = new ProduceConsumerV2();

        new Thread("PRODUCE ") {
            @Override
            public void run() {
                while (true) {
                    produceConsumerV2.produce();
                }
            }
        }.start();

        new Thread("CONSUMER") {
            @Override
            public void run() {
                while (true) {
                    produceConsumerV2.consumer();
                }
            }
        }.start();
    }*/
}
