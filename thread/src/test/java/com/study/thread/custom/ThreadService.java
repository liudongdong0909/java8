package com.study.thread.custom;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/18
 */
public class ThreadService {

    private Thread executeThread;

    private boolean finished = false;

    public void execute(Runnable task) {
        // 创建一个执行线程
        executeThread = new Thread() {

            @Override
            public void run() {
                // 创建一个执行线程的守护线程, 执行具体任务
                Thread runner = new Thread(task);
                runner.setDaemon(true);
                runner.start();
                // 为了避免执行线程结束，守护线程还没有执行的情况，当前线程(执行线程)join();
                // 当前线程等待所有守护线程执行完毕。
                // executeThread 等待 runner 执行完毕。
                try {
                    runner.join();
                    finished = true;
                } catch (InterruptedException e) {
                    // e.printStackTrace();
                }
            }
        };
        executeThread.start();
    }

    public void shutdown(long millis) {
        long currentTime = System.currentTimeMillis();
        // 任务未完成
        while (!finished) {
            // 执行时间 大于等于 millis
            if ((System.currentTimeMillis() - currentTime) >= millis) {
                System.out.println("任务超时，需要结束");
                executeThread.interrupt();
                break;
            }

            try {
                executeThread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("执行线程被中断");
                break;
            }
        }

        finished = false;
    }
}
