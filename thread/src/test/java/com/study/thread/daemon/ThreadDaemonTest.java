package com.study.thread.daemon;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/18
 */
public class ThreadDaemonTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(DeamonThread.class);

    /**
     * 在Java中有两类线程：User Thread(用户线程)、Daemon Thread(守护线程)
     *
     * 守护线程：
     *      用个比较通俗的比如，任何一个守护线程都是整个JVM中所有非守护线程的保姆：
     *
     *  只要当前JVM实例中尚存在任何一个非守护线程没有结束，守护线程就全部工作；只有当最后一个非守护线程结束时，守护线程随着JVM一同结束工作。
     * Daemon的作用是为其他线程的运行提供便利服务，守护线程最典型的应用就是 GC (垃圾回收器)，它就是一个很称职的守护者。
     * User和Daemon两者几乎没有区别，唯一的不同之处就在于虚拟机的离开：如果 User Thread已经全部退出运行了，只剩下Daemon Thread存在了，虚拟机也就退出了。 因为没有了被守护者，Daemon也就没有工作可做了，也就没有继续运行程序的必要了。
     *
     * @param args
     */

    public static void main(String[] args) {

        /**
         *
         * SERVER A  <-------------->  SERVER B
         *
         *  --> daemonThread(health check)
         *
         */

        Thread connectionThread = new Thread(() -> {

            LOGGER.info("connection thread start .");

            Thread heartBeatThread = new Thread(() -> {
                try {
                    while (true) {
                        LOGGER.info("do something for health check . ");
                        Thread.sleep(1_000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            /**
             * setDaemon(boolean on ) 如果为真，则将此线程标记为守护线程, 默认为false
             *
             * 1. 将此线程标记为守护进程线程或用户线程。当运行的唯一线程都是守护进程线程时，Java虚拟机退出。
             *          即: 还运行有非守护线程时，JVM不能退出。
             *
             * 2. 必须在线程启动之前调用此方法。
             *  否则 抛出异常 -- 表示这个线程是活的
             *  Exception in thread "Thread-0" java.lang.IllegalThreadStateException
             *
             *3. setDaemon() 抛出的第二个异常
             *  SecurityException——如果checkAccess()确定当前线程不能修改这个线程
             *
             *   在Daemon线程中产生的新线程也是Daemon的。
             */

            heartBeatThread.setDaemon(true);
            heartBeatThread.start();

            try {
                Thread.sleep(1_000);
                LOGGER.info("connection thread finished done .");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        connectionThread.start();

    }
}
