package com.donggua.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * java8 Lambda 表达式学习
 *
 * @author IT_donggua
 * @version V1.0
 * @create 2017-08-11 下午 02:49
 */
public class Lambda {

    @Test
    public void test() {
        // 使用匿名内部类作为参数传递
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        };

        TreeSet<String> treeSet = new TreeSet<>(comparator);

        // 或者 Lambda 表达式作为参数传递
        TreeSet<String> treeSet1 = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });
    }

    @Test
    public void test2() {
        // 使用Lambda 表达式作为参数传递
        Comparator<String> comparator = (x, y) -> Integer.compare(x.length(), y.length());
        TreeSet<String> treeSet = new TreeSet<>(comparator);
    }

    @Test
    public void test3() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello word");
            }
        };
        runnable.run();

        System.out.println("=================");

        Runnable runnable2 = () -> System.out.println("hello word lambda");
        runnable2.run();
    }


}
