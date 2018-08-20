package com.study.thread.produceconsumer.v1;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/19
 */
public class ProduceConsumerV1 {

    private int i = 1;

    private final Object LOCK = new Object();

    private void produce() {
        synchronized (LOCK) {
            System.out.println("PRODUCE -> " + i++);
        }
    }

    private void consumer() {
        synchronized (LOCK) {
            System.out.println("CONSUMER -> " + i);
        }
    }

    public static void main(String[] args) {

        /**
         * 线程间没有通信，造成produce一直再生产新的数据，但是consumer只消费了最新的数据，produce生产的其他数据都丢失了。
         */

        ProduceConsumerV1 produceConsumerV1 = new ProduceConsumerV1();

        new Thread("PRODUCE") {
            @Override
            public void run() {
                while (true) {
                    produceConsumerV1.produce();
                }
            }
        }.start();

        new Thread("CONSUMER") {
            @Override
            public void run() {
                while (true) {
                    produceConsumerV1.consumer();
                }
            }
        }.start();
    }

}


