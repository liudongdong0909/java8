package com.study.thread.group;

import java.util.Arrays;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/19
 */
public class ThreadGroupTest {

    /**
     * public class ThreadGroup extends Object implements Thread.UncaughtExceptionHandler
     *
     * 线程组表示一组线程。此外，线程组还可以包括其他线程组。
     * 线程组形成一个树，其中除了初始线程组以外的每个线程组都有一个父线程组
     *
     * 线程被允许访问它自己的线程组的信息，但不能访问它的线程组的父线程组或任何其他线程组的信息
     *
     * 1. 除了初始线程组以外，每个线程组都有一个父线程
     * 2. 线程只能访问它自己的线程组信息。-> 测试一把，只读信息还是可以访问的
     */

    /**
     * public ThreadGroup(ThreadGroup parent, String name)
     * 创建一个新的线程组。这个新组的父组是指定的线程组。
     *
     * 调用父线程组的checkAccess方法，不带参数;这可能导致安全异常。
     *
     */

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getThreadGroup().getName());

        ThreadGroup threadGroup = new ThreadGroup("TG1");

        Thread  t1 = new Thread(threadGroup, "t1"){
            @Override
            public void run() {
                // while (true){
                //     System.out.println(getThreadGroup().getName());
                //     System.out.println(getThreadGroup().getParent());
                // }
            }
        };

        t1.start();


        ThreadGroup threadGroup2 = new ThreadGroup("TG2");
        Thread t2 = new Thread(threadGroup2, "t2"){
            @Override
            public void run() {
                System.out.println(threadGroup.getName());
                System.out.println(threadGroup.activeCount());

                Thread[] threads = new Thread[threadGroup.activeCount()];

                threadGroup.enumerate(threads);

                Arrays.asList(threads).forEach(System.out::println);
            }
        };
        t2.start();
    }
}
