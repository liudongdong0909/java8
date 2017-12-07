package com.donggua.thread;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池测试
 *
 * @author IT_donggua
 * @version V1.0
 * @create 2017-12-06 下午 01:21
 */
public class ThreadPool {

    /**
     * newCacheThreadPool:
     * 创建一个可缓存的无界线程池，该方法无参数。
     * 当线程池中的线程空闲时间超过60s则会自动回收该线程，
     * 当任务超过线程池的线程数则会创建线程。 线程池的大小上限：Integer.MAX_VALUE
     * <p>
     * 执行结果：
     * pool-1-thread-1====>>>>>0
     * pool-1-thread-1====>>>>>1
     * pool-1-thread-1====>>>>>2
     * pool-1-thread-1====>>>>>3
     */
    @Test
    public void test1() {
        // 对于超出的线程会在LinkedBlockingQueue队列中等待
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 10,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new ThreadFactory() {
            private final AtomicInteger poolNumber = new AtomicInteger(1);
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {

                return new Thread(r, "cacheThreadPool-" + poolNumber.getAndIncrement() + "-thread-" + threadNumber.getAndIncrement());
            }
        });
        //  ExecutorService threadPoolExecutor = Executors.newCachedThreadPool();
        for (int i = 0; i < 200; i++) {
            final int index = i;
            threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName() + "====>>>>>" + index));
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        threadPoolExecutor.shutdown();

        System.out.println(">>>>>>>>==========================<<<<<<<<");
    }

    /**
     * 执行结果：
     * Thread-0====>>>>>0
     * Thread-1====>>>>>1
     * Thread-2====>>>>>2
     * Thread-3====>>>>>3
     */
    @Test
    public void test2() {
        for (int i = 0; i < 20; i++) {
            final int index = i;
            Thread threadTest = new Thread(() -> System.out.println(Thread.currentThread().getName() + "====>>>>>" + index));
            threadTest.start();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * newFixedThreadPool
     * 默认keepAliveTime：0,
     * 默认超出的线程会在 LinkedBlockingQueue队列中等待
     * 执行结果：
     * <p>
     * pool-1-thread-1====>>>>0
     * pool-1-thread-2====>>>>1
     * pool-1-thread-3====>>>>2
     * pool-1-thread-4====>>>>3
     */
    @Test
    public void test3() {
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger poolNumber = new AtomicInteger(1);
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "cacheThreadPool-" + poolNumber.getAndIncrement() + "-thread-" + threadNumber.getAndIncrement());
            }
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 10, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), threadFactory);
        //ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < 20; i++) {
            final int index = i;
            threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName() + "====>>>>" + index));

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        threadPoolExecutor.shutdown();
    }

    /**
     * newScheduledThreadPool
     * 创建一个可定时执行或周期执行任务的线程池，该方法可以指定线程池的核心线程个数
     */
    @Test
    public void test4() {
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger poolNumber = new AtomicInteger(1);
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "scheduledThreadPool-" + poolNumber.getAndIncrement() + "-thread-" + threadNumber.getAndIncrement());
            }
        };

       ScheduledThreadPoolExecutor scheduledThreadPool = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), threadFactory);
        //ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(0;
        for (int i = 0; i < 20; i++) {
            // 定时执行一次的任务， 延迟1s后执行
            /**
             * 执行结果：
             *
             * pool-1-thread-1====>>>>, dely 1s
             * pool-1-thread-1====>>>>, dely 1s
             * pool-1-thread-4====>>>>, dely 1s
             * pool-1-thread-2====>>>>, dely 1s
             * pool-1-thread-1====>>>>, dely 1s
             * pool-1-thread-3====>>>>, dely 1s
             *
             */
            scheduledThreadPool.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "====>>>>" + "1, dely 1s");
                }
            }, 1, TimeUnit.SECONDS);

            // 周期性地执行任务，延迟2s后， 每3s一次周期性的执行任务
            // 周期时间间隔是以上一个任务开始执行到下一个任务开始执行的间隔，也就是这一些任务系列的触发时间都是可预知的。
            scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "====>>>>" + "2, every 3s");
                }
            }, 2, 3, TimeUnit.SECONDS);
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 周期时间间隔是以上一个任务执行结束到下一个任务开始执行的间隔
            scheduledThreadPool.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "====>>>>" + "3, every 3s");
                }
            }, 2, 3, TimeUnit.SECONDS);
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        scheduledThreadPool.shutdown();
    }

    @Test
    public void test5() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "singleThreadPool-" + new AtomicInteger(1).getAndIncrement() + "-thread-");
            }
        });
        // ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 20; i++) {
            final int index = 1;
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "====>>>>" + index);
                }
            });
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class SingleThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        SingleThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "singleThreadPool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

}
