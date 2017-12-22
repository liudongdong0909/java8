package com.donggua.single;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-12-15 下午 03:38
 */
public class Singleton {

    private static volatile Singleton singleton = null;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (null == singleton) {
            synchronized (Singleton.class) {
                singleton = new Singleton();
            }
        }
        return singleton;
    }
}
