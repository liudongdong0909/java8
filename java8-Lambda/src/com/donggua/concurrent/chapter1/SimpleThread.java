package com.donggua.concurrent.chapter1;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2018-03-08 下午 01:29
 */
public class SimpleThread {

    /**
     * 并发
     * 读取数据库
     * 写入文件
     */

    public static void main(String[] args) {

        new Thread("READ-THREAD"){
            @Override
            public void run() {
                println(Thread.currentThread().getName());
                readFromDataBase();
            }
        }.start();

        new Thread("WRITE-THREAD"){
            @Override
            public void run() {
                println(Thread.currentThread().getName());
                writeDataToFile();
            }
        }.start();

    }

    public static void readFromDataBase() {
        /**
         * read data from database and handle it.
         */
        try {
            println("begin read data from database ... ");
            Thread.sleep(1000 * 30L);
            println("read data done and start handle it ... ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        println("The data handle finish and successfully ... ");
    }

    public  static  void writeDataToFile(){
        try {
            println("begin write data to file");
            Thread.sleep(2000 * 20L);
            println("write data done and start handle it ...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        println("The data handle finish and successfully ... ");
    }

    public static void println(String message) {
        System.out.println(message);
    }
}
