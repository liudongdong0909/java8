package com.study.thread.juc.cb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/25
 */
public class CyclicBarrierTest1 {

    /**
     * 一种同步辅助程序，允许一组线程相互等待，以达到共同的障碍点。循环屏障在涉及固定大小的线程组的程序中非常有用，这些线程组有时必须彼此等待。
     * 这个障碍称为循环障碍，因为它可以在等待线程被释放之后重用。
     * <p>
     * <p>
     * 循环屏障支持可选的Runnable命令，该命令在每一个屏障点上运行一次，在一方的最后一个线程到达之后，
     * 但在释放任何线程之前。这种障碍行动有助于在任何一方继续进行之前更新共享状态。
     */

    public static final Logger LOGGER = LoggerFactory.getLogger(CyclicBarrierTest1.class);

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            LOGGER.info("all thread finished ");
        });

        new Thread("T1") {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(20);
                    LOGGER.info("T1 -> finished .");
                    cyclicBarrier.await();
                    LOGGER.info("T1 -> the others thread finished too. ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread("T2") {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    LOGGER.info("T2 -> finished .");
                    cyclicBarrier.await();
                    LOGGER.info("T2 -> the others thread finished too. ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        for (; ; ) {
            System.out.println("waiting number -> " + cyclicBarrier.getNumberWaiting());
            System.out.println(" parties -> " + cyclicBarrier.getParties());
            System.out.println("is broken ->" + cyclicBarrier.isBroken());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
