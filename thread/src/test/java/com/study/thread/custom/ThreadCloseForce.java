package com.study.thread.custom;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/18
 */
public class ThreadCloseForce {

    public static void main(String[] args) {

        ThreadService threadService = new ThreadService();

        long startTime = System.currentTimeMillis();

        threadService.execute(() -> {

             // 1. 一直完不成
            // while (true){
            //     // TODO
            //
            // }


            // 2. 10秒内完成
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        });

        threadService.shutdown(10000);

        long endTime = System.currentTimeMillis();
        System.out.println("执行时间 ：" + (endTime - startTime));
    }
}
