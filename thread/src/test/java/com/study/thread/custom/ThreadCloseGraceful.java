package com.study.thread.custom;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/18
 */
public class ThreadCloseGraceful {

    public static class Worker extends Thread {

        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.interrupted());
                if (Thread.interrupted()){
                    System.out.println("已中断");
                    break; // return 就无法执行后面的逻辑
                }
            }
            // 中断之后还可以执行其他的逻辑序列。。。。
            System.out.println("set up 1");
            System.out.println("set up 2");
        }
    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        worker.interrupt();
    }
}
