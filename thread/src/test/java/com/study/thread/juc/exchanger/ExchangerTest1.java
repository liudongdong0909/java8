package com.study.thread.juc.exchanger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Exchanger;

/**
 * $$
 *
 * 一个同步点，在这个同步点上，线程可以对 成对的元素 进行配对和交换。每个线程在进入exchange方法时显示一些对象，与一个合作线程匹配，并在返回时接收其合作线程的对象。
 * 交换器可以看作是同步队列的双向形式。交换器在遗传算法和管道设计等应用中可能很有用。
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/25
 */
public class ExchangerTest1 {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExchangerTest1.class);

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger();

        new Thread(() ->{
            LOGGER.info(Thread.currentThread().getName() + " start.");
            try {
                /**
                 * 等待另一个线程到达这个交换点(除非当前线程被中断)，然后将给定的对象传输给它，并接收它的对象作为回报。
                 *
                 * 如果另一个线程已经在交换点等待，那么出于线程调度的目的，它将恢复，并接收由当前线程传入的对象。当前线程立即返回，接收由另一个线程传递给exchange的对象。
                 *
                 * 如果没有其他线程已经在交换器中等待，那么出于线程调度的目的，当前线程将被禁用并处于休眠状态，直到发生两种情况之一:
                 *  1. 其他线程进入交换器;
                 *  2. 或者其他线程中断当前线程。
                 *
                 * 如果当前线程:
                 *  1. 在进入此方法时设置其中断状态;
                 *  2. 或者在等待交换时中断，然后抛出InterruptedException，并清除当前线程的中断状态。
                 */
                String exchange = exchanger.exchange("I am come form T - A");
                LOGGER.info(Thread.currentThread().getName() + "get value : " + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info(Thread.currentThread().getName() + " end .");
        }, "T - A").start();

        new Thread(() ->{
            LOGGER.info(Thread.currentThread().getName() + " start.");
            try {
                String exchange = exchanger.exchange("I am come form T - B");
                LOGGER.info(Thread.currentThread().getName() + "get value : " + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info(Thread.currentThread().getName() + " end .");
        }, "T - B").start();
    }
}
