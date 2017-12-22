package com.donggua.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-12-19 下午 04:26
 */
public class Fibonacci {

    static Map<Integer, Integer> cache = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println("f(" + i + ") = " + finonac3(i));
        }
    }

    static int finonac(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        System.out.println("Calculating f(" + i + ")");
        return finonac(i - 2) + finonac(i - 1);
    }

    static int finonac2(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }

        return cache.computeIfAbsent(i, (key) -> {
            System.out.println("Slow calculation of " + key);
            return finonac2(i - 2) + finonac2(i - 1);
        });
    }

    static volatile Map<Integer, Integer> cache2 = new HashMap<>();

    static int finonac3(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        Integer result = cache2.get(i);
        if (null == result) {
            synchronized (cache2) {
                result = cache2.get(i);
                if (null == result) {
                    System.out.println("Slow calculation of " + i);
                    result = finonac3(i - 2) + finonac3(i - 1);
                    cache2.put(i, result);
                }
            }
        }
        return result;
    }
}
