package com.study.thread.completion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/27
 */
public class CompletionServiceTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

       // futureDefect();

        testCompletionService();
    }

    public static void testCompletionService() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        final List<Callable<Integer>> callableList = Arrays.asList(
                () -> {
                    sleep(5);
                    System.out.println(" 数据是5的任务");
                    return 5;
                },
                () -> {
                    sleep(10);
                    System.out.println(" 数据是10的任务");
                    return 10;
                }
        );

        ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(executorService);

        List<Future<Integer>> futureList = new ArrayList<>();
        callableList.forEach(callable -> {
                    Future<Integer> future = executorCompletionService.submit(callable);
                    futureList.add(future);
                });

        // Future<Integer> future = null;
        // while ((future = executorCompletionService.take()) != null){
        //     System.out.println(future.get());
        // }

        Future<Integer> future = executorCompletionService.poll();
        System.out.println(future.get());

        // Future<Integer> future1 = executorCompletionService.poll(30, TimeUnit.SECONDS);
        // System.out.println(future1.get());

    }




    public static void futureDefect() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        final List<Callable<Integer>> callableList = Arrays.asList(
                () -> {
                    sleep(5);
                    System.out.println(" 数据是5的任务");
                    return 5;
                },
                () -> {
                    sleep(10);
                    System.out.println(" 数据是10的任务");
                    return 10;
                }
        );

        // callableList.forEach(callable -> {
        //     try {
        //         System.out.println("==============");
        //         Future<Integer> future = executorService.submit(callable);
        //         System.out.println(future.get());
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     } catch (ExecutionException e) {
        //         e.printStackTrace();
        //     }
        // });

        try {
            List<Future<Integer>> futures = executorService.invokeAll(callableList);

            System.out.println(futures.get(0).get());
            System.out.println(futures.get(1).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    public static void sleep(long sleepTimes) {
        try {
            TimeUnit.SECONDS.sleep(sleepTimes);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
