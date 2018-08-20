package com.study.thread.juc.cdl;

import java.util.Random;
import java.util.concurrent.*;

/**
 * $$ 一言以蔽之： 一个串行化的任务中， 有一部或者某些地方可以并行处理， 可以考虑使用CountDownLatch
 *
 *
 * <p>
 * 一种同步辅助程序，允许一个或多个线程等待，直到在其他线程中执行的一组操作完成。
 * <p>
 * 使用给定的计数初始化CountDownLatch。await方法会阻塞，直到由于调用countDown()方法而使当前计数变为0为止，在此之后，所有等待线程都被释放，并且任何后续的wait返回调用都会立即返回。
 * 这是一个一次性的现象——计数不能被重置。如果需要重置计数的版本，请考虑使用循环屏障.
 * <p>
 * CountDownLatch是一个多功能的同步工具，可以用于多种目的。使用一个计数初始化的CountDownLatch作为简单的on/off闩锁，或gate:所有调用它的线程在门上等待，直到它被一个调用countDown()的线程打开。
 * 一个初始化为N的CountDownLatch可以让一个线程等待，直到N个线程完成某个动作，或者某个动作已经完成N次。
 * <p>
 * CountDownLatch的一个有用特性是，它不要求调用倒计时的线程在执行前等待计数达到零，它只是阻止任何线程在所有线程都可以通过之前继续等待。
 * <p>
 * <p>
 * 内存一致性影响:直到计数为0之前，在调用countDown()之前线程中的动作发生在另一个线程中相应的await()成功返回之后。
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/20
 */
public class CountDownLatchTest {

    public static void main(String[] args) {

        // 1. 装配需处理的数据 。。。 很复杂的业务逻辑
        int[] data = query();

        // 2. 多线程处理装配之后的数据 。。。数据量很大
        for (int i = 0; i < data.length; i++) {
            executor.execute(new SimpleRunnable(data, i, countDownLatch));
        }

        try {
            /**
             * 使当前线程等待直到闩锁计数为零，除非线程被中断。
             *
             * 如果当前计数为0，则此方法立即返回。
             *
             * 如果当前计数大于零，那么出于线程调度的目的，当前线程将被禁用并处于休眠状态，直到发生以下两种情况之一:
             *  1. 由于调用了countDown()方法，计数达到零;
             *  2. 其他一些线程中断当前线程。
             *
             * 如果当前线程:
             *  1. 在进入此方法时设置其中断状态;
             *  2. 被打断而等待,
             * 然后抛出InterruptedException，清除当前线程的中断状态。
             *
             */
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 3. 将第2步处理完的数据进行其他业务逻辑操作，比如：入库。。。。数据量很大
        System.out.println(" 所有数据操作执行完毕");
        executor.shutdown();

        /**
         *
         * 启动一个有序的关闭，之前提交的任务将被执行，但是不会接受新的任务。如果已经关闭，则调用没有其他效果。
         * 此方法不等待之前提交的任务完成执行。使用awaitTermination来完成这项工作。
         *
         * shutdown()并不会阻塞线程 执行顺序。只是可以关闭空闲线程，不再接受新的任务。
         */
        // executor.shutdown();
        // try {
        //     /**
        //      * 阻塞，直到所有任务在关闭请求后完成执行，或超时发生，或当前线程被中断，无论哪个线程最先发生。
        //      */
        //     executor.awaitTermination(5, TimeUnit.MINUTES);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        // 3. 将第2步处理完的数据进行其他业务逻辑操作，比如：入库。。。。数据量很大
        // System.out.println(" 所有数据操作执行完毕");

        /**
         * 期望的执行顺序是 1 -> 2 -> 3
         * 实际上 。。。。 2里面的执行没有block住，3在2之前执行, 而且线程还不释放
         *
         * 所有数据操作执行完毕
         *   pool-1-thread-1处理完毕 -> 0
         *   pool-1-thread-4处理完毕 -> 3
         *   pool-1-thread-3处理完毕 -> 2
         *
         * 第一种方案：
         *  在3之前进行shutdown() -> 好像执行顺序并没有变化，只是空闲线程可以关闭
         *  优化 : 在shutdown() 之后 执行 excutor.awaitTermination(timeout, TimeUnit)
         *
         *  执行结果：
         *
         *          pool-1-thread-4处理完毕 -> 9
         *          pool-1-thread-2处理完毕 -> 6
         *          pool-1-thread-3处理完毕 -> 7
         *          pool-1-thread-1处理完毕 -> 10
         *          所有数据操作执行完毕
         *
         * 第二种方案：
         *     CountDownLauch
         *
         *
         */


    }

    private static Random random = new Random(System.currentTimeMillis());

    private static ExecutorService executor = Executors.newFixedThreadPool(4);

    private static final CountDownLatch countDownLatch = new CountDownLatch(11);

    static class SimpleRunnable implements Runnable {

        private final int[] data;

        private final int index;

        private CountDownLatch countDownLatch;

        SimpleRunnable(int[] data, int index, CountDownLatch countDownLatch) {
            this.data = data;
            this.index = index;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int value = data[index];
            if (value % 2 == 0) {
                data[index] = value * 2;
            } else {
                data[index] = value * 3;
            }
            System.out.println(Thread.currentThread().getName() + "处理完毕 -> " + index);

            /**
             * 减少锁存器的数量，当数量达到零时释放所有等待线程。
             *
             * 1. 如果当前计数大于零，那么它就会递减。如果新计数为零，那么为了线程调度目的，将重新启用所有等待线程。
             *
             * 2. 如果当前计数等于0，那么什么也不会发生。
             *
             */
            countDownLatch.countDown();
        }
    }

    private static int[] query() {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    }
}
