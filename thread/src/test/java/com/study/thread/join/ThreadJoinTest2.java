package com.study.thread.join;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/18
 */
public class ThreadJoinTest2 {

    public static void main(String[] args) throws InterruptedException {

        long startTiem = System.currentTimeMillis();

        Thread t1 = new Thread(new CaptureRunnable("M1", 10000L));
        Thread t2 = new Thread(new CaptureRunnable("M2", 40000L));
        Thread t3 = new Thread(new CaptureRunnable("M3", 20000L));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        long endTime = System.currentTimeMillis();

        System.out.printf("save data begin timestamp is : %s , end timestamp is : %s", startTiem, endTime);
    }



}

class CaptureRunnable implements Runnable{

    private String machineName;

    private long spendTime;

    public CaptureRunnable(String machineName, long spendTime) {
        this.machineName = machineName;
        this.spendTime = spendTime;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(spendTime);
            System.out.println();
            System.out.printf( machineName + "completed data capture at timestamp [ %s ] and successfully.", System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public  String getResult (){
        return machineName + "finished .";
    }
}