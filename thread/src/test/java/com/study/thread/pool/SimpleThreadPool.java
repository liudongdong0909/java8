package com.study.thread.pool;

/**
 * $$
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/19
 */
public class SimpleThreadPool {

    private final int size;

    private static final int DEFAULT_SIZE = 10;

    public SimpleThreadPool() {
        this(DEFAULT_SIZE);
    }

    public SimpleThreadPool(int size) {
        this.size = size;
    }
}
