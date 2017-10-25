package com.donggua.date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by donggua on 2017/8/20.
 */
public class DateFormat {

    @Test
    public void test1() throws Exception {
        // SimpleDateFormat 存在线程安全问题
        // java.util.concurrent.ExecutionException: java.lang.NumberFormatException: For input string: "2172170E4"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Callable<Date> task = () -> sdf.parse("20170820");

        ExecutorService pool = Executors.newFixedThreadPool(50);
        List<Future<Date>> results = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            results.add(pool.submit(task));
        }

        for (Future<Date> result : results) {
            System.out.println(result.get());
        }

        pool.shutdown();
    }

    @Test
    public void test3() {

        Callable<Date> task = () -> DateFormatThreadLocal.convert("20170820");

        ExecutorService pool = Executors.newFixedThreadPool(50);
        List<Future<Date>> results = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            results.add(pool.submit(task));
        }

        results.stream()
                .forEach((d) -> {
                    try {
                        System.out.println(d.get());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        pool.shutdown();
    }

    //java 时间包
    @Test
    public void test2() throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

        Callable<LocalDate> task = () -> LocalDate.parse("20170820", dtf);

        // 推荐使用 ThreadPoolExecutor 创建线程池。
        // 因为：
        // 说明： Executors 返回的线程池对象的弊端如下：
        // 1 ） FixedThreadPool 和 SingleThreadPool:
        // 允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。
        // 2） CachedThreadPool 和 ScheduledThreadPool:
        // 允许的创建线程数量为 Integer.MAX_VALUE， 可能会创建大量的线程，从而导致 OOM。
        //
        ThreadPoolExecutor pool = new ThreadPoolExecutor(50,50,20,TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(1024),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        //ExecutorService pool = Executors.newFixedThreadPool(50);

        List<Future<LocalDate>> results = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            results.add(pool.submit(task));
        }

        for (Future<LocalDate> future : results) {
            System.out.println(future.get());
        }

        pool.shutdown();
    }


}
