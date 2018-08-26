package com.study.thread.juc.semaphore;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * $$
 * <p>
 * 计数信号量。
 * 从概念上讲，信号量维护一组许可。如果有必要，每个get()块直到许可证可用，然后取它。
 * 每个release()都添加一个许可证，可能释放一个阻塞的收购者。但没有实际使用许可证对象;信号量只是保持可用数量的计数，并采取相应的行动。
 * <p>
 * 信号量通常用于限制线程数量，而不能访问某些(物理或逻辑)资源。
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/26
 */
public class SemaphoreTest3 {

    public static final Logger LOGGER = LoggerFactory.getLogger(SemaphoreTest3.class);

    public static void main(String[] args) throws InterruptedException {

        //Semaphore semaphore = new Semaphore(2);

        MySemaphore semaphore = new MySemaphore(2);

        new Thread(() -> {
            LOGGER.info(Thread.currentThread().getName() + "running");
            try {
                /**
                 * 获取并返回所有立即可用的许可证。
                 */
                semaphore.drainPermits();
                LOGGER.info(Thread.currentThread().getName() + "get #semaphore lock .");
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release(1);
                LOGGER.info(Thread.currentThread().getName() + "released . ");
            }

        }, "T1").start();


        TimeUnit.MICROSECONDS.sleep(40);

        new Thread(() -> {
            LOGGER.info(Thread.currentThread().getName() + "running");
            try {

                // semaphore.acquire();

                /**
                 * 只有在调用时可用时，才能从该信号量获得许可。
                 *
                 * 获得许可证，如果有许可证，立即返回，值为true，可用许可证数量减少1
                 *
                 * 如果没有许可证可用，那么此方法将立即返回值为false
                 *
                 * 即使将此信号量设置为使用公平的订购策略，如果有许可，对tryAcquire()的调用将立即获得许可，无论其他线程是否正在等待。
                 * 这种“撞船”行为在某些情况下可能有用，即使它破坏了公平。
                 * 如果您想要尊重公平设置，那么使用tryAcquire(0, timeunits . seconds)，这几乎是等价的(它还检测中断)。
                 */
                boolean result = semaphore.tryAcquire();
                System.out.println(result ? "SUCCESSFUL" : "FAILURE");
                // LOGGER.info(Thread.currentThread().getName() + "get #semaphore lock .");
                // TimeUnit.SECONDS.sleep(3);
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            } finally {
                semaphore.release();
                LOGGER.info(Thread.currentThread().getName() + "released . ");
            }

        }, "T2").start();

        TimeUnit.SECONDS.sleep(2);

        /**
         * 查询是否有线程正在等待获取。注意，由于任何时候都可能发生取消，所以真正的返回不能保证任何其他线程都能获得。
         *
         * 该方法主要用于系统状态监测。
         */
        LOGGER.info( "是否有等待线程：" + semaphore.hasQueuedThreads());

        /**
         * 返回一个包含等待获取的线程的集合。因为在构造这个结果时，实际的线程集可能会动态变化，所以返回的集合只是一个最佳估计。
         * 返回集合的元素没有特定的顺序。
         *
         * 这种方法的目的是为了便于构建提供更广泛的监视设施的子类。
         */
        Collection<Thread> waitingThreads = semaphore.getWaitingThreads();
        waitingThreads.forEach(t -> {
            System.out.println(t.getName());
            System.out.println(JSONObject.toJSON(t));
        });
    }

    static class MySemaphore extends Semaphore {

        public MySemaphore(int permits) {
            super(permits);
        }

        public MySemaphore(int permits, boolean fair) {
            super(permits, fair);
        }

        protected Collection<Thread> getWaitingThreads() {
            return super.getQueuedThreads();
        }
    }
}
