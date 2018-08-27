package com.study.thread.future;

import java.util.concurrent.*;

/**
 * Future表示异步计算的结果。提供了用于检查计算是否完成、等待其完成以及检索计算结果的方法。
 * 只有在计算完成后，才能使用方法get检索结果，如果需要，则阻塞，直到准备好为止。
 * 取消由cancel方法执行。还提供了其他方法来确定任务是否正常完成或取消。一旦计算完成，就不能取消计算。
 * 如果出于可取消性的考虑而不想提供可用的结果，那么可以声明表单 Future<?> 的类型作为基础任务的结果 和返回null。
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/27
 */
public class FutureTest1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // testGet();
        //testGetWithTimeOut();
        // testIsDone();
        // testCancel();
        testCancel2();
    }

    public static void testGet() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        });

        // 去做一些其他的事情
        System.out.println("===============================》》》》》》");

        Thread callerThread = Thread.currentThread();

        new Thread(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            callerThread.interrupt();
        }).start();

        try {
            /**
             * 如果有必要，等待计算完成，然后检索其结果。
             */
            Integer result = future.get();
            System.out.println("==== " + result + " ====");
            // 打断是谁打断？ main
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public static void testGetWithTimeOut() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("==============");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        });

        try {
            // 超时之后，线程的任务其实还在继续执行。。。。。。 这就是个大问题了
            /**
             * 如果有必要，最多要等待给定的时间，以便完成计算，然后如果可用，检索其结果。
             */
            future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }


    public static void testIsDone() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("==============");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        });

        System.out.println(future.get());
        System.out.println(future.isDone());
    }

    public static void testCancel(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("==============");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        });

        System.out.println(future.cancel(true));
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
    }

    public static void testCancel2() throws ExecutionException, InterruptedException {
        // ExecutorService executorService = Executors.newFixedThreadPool(5, r -> {
        //     Thread thread = new Thread(r);
        //     thread.setDaemon(true);
        //     return thread;
        // });
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Future<Integer> future = executorService.submit(() -> {
                while (true){

                }
            //return 5;
        });

        /**
         * 试图取消此任务的执行。如果任务已经完成、已被取消或由于其他原因无法取消，则此尝试将失败。如果成功，并且在调用cancel时该任务尚未启动，则该任务永远不会运行。
         * 如果任务已经启动，那么mayInterruptIfRunning参数确定执行此任务的线程是否应该中断，以尝试停止任务。
         *
         * 在此方法返回之后，对isDone()的后续调用将始终返回true。如果这个方法返回true，那么对iscancelled()的后续调用将始终返回true。
         */
        System.out.println(future.cancel(true));
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
        System.out.println(future.get());
    }
}
