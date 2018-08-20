package com.study.thread.join;

import java.util.stream.IntStream;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/18
 */
public class ThreadJoinTest {

    /**
     * join()
     *  等待线程死亡;
     *      即： 当前线程等待所有子线程结束之后，再执行当前线程。
     *  此方法的调用与调用的行为完全相同
     *
     * @param args
     */

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            IntStream.range(1, 1000)
                    .forEach(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
        });

        Thread t2 = new Thread(() -> {
            IntStream.range(1, 1000)
                    .forEach(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
        });

        t1.start();
        t2.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 多个子线程之间是不存在执行顺序的。
         * 当所有的线程都执行完毕， 当前线程才开始执行。
         */

        System.out.println("=== all child thread finished done. === ");

        IntStream.range(1, 1000)
                .forEach(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));

    }
}
